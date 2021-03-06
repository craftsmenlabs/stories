package demo.data.importer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Files;
import org.craftsmenlabs.stories.api.models.StoriesReport;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.ReportConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardReporter;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.launcher.BootApp;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is a make shift jira file importer for demo purposes,
 * not a test
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootApp.class})
@TestPropertySource(locations = "classpath:application-test.yml")
public class importJiraJson {
    @Autowired
    private StorynatorConfig storynatorConfig;


    @Ignore
    @Test
    public void importData() {
        Files.fileNamesIn(storynatorConfig.getSource().getFile().getLocation(), false).stream()
                .map(File::new)
                .filter(file -> !file.getName().startsWith("."))
                .forEach(this::importFile);
    }


    //accept file in the format:
    // <projectToken>_YYYY-MM-DD_HH-MM.json
    // projectToken_2016-02-13_16-00.json
    public void importFile(File file) {
        System.out.println("importing " + file.getName() + " with:");

        String[] split = file.getName().split("_|\\.");
        if (split.length != 4) {
            throw new IllegalArgumentException(
                    file.getName() + " - invalid format! \n" +
                            " Format should be: <projectToken>_YYYY-MM-DD_HH-MM.json\n" +
                            "Example:  projectToken_2016-02-13_16-00.json\n");
        }

        //generate a random token in the dashboard,
        // then insert the old token and the new one here.
        //Do not commit this
        Map<String, String> tokenNameMapping = new HashMap<String, String>(){{
            put("examples"  , "58663d71a584fa0c92e5a6cc");
            put("examples-2", "58663d6ea584fa0c92e5a6cb");
            put("androidApp", "58663d7ea584fa0c92e5a6cf");
            put("App"       , "58663e10a584fa0c92e5a6d1");
            put("sales"     , "58663d82a584fa0c92e5a6d0");
            put("webshop"   , "58663d79a584fa0c92e5a6ce");
            put("WebshopBE" , "58663d76a584fa0c92e5a6cd");
        }};

        String projectToken = tokenNameMapping.getOrDefault(split[0], split[0]);
        System.out.println("project token: " + projectToken);

        String date = split[1];
        List<Integer> time = Arrays.stream(split[2].split("-"))
                .map(Integer::parseInt).collect(Collectors.toList());

        LocalDateTime dateTime = LocalDate.parse(date).atTime(time.get(0), time.get(1)).plus(1, ChronoUnit.MONTHS);
        System.out.println("date time: " + dateTime);

        ValidationConfig validationConfig = storynatorConfig.getValidation();
        System.out.println("validation config: " + validationConfig);

        FieldMappingConfig fieldMappingConfigCopy = storynatorConfig.getFieldMapping();
        System.out.println("fieldmapping: " + fieldMappingConfigCopy);

        ReportConfig reportConfig = storynatorConfig.getReport();
        reportConfig.getDashboard().setToken(projectToken);
        System.out.println("reporting config: " + reportConfig);


        ObjectMapper mapper = new ObjectMapper();
        JiraBacklog jiraBacklog = null;
        try {
            jiraBacklog = mapper.readValue(file, JiraBacklog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JiraJsonParser jiraJsonParser = new JiraJsonParser(new StandaloneLogger(), fieldMappingConfigCopy, storynatorConfig.getFilter(), storynatorConfig.getSource());

        Backlog backlog = jiraJsonParser.parse(jiraBacklog);

        BacklogScorer scorer = new BacklogScorer(validationConfig, new CurvedRanking());
        ValidatedBacklog validatedBacklog = scorer.validate(backlog);

        StoriesReport storiesReport = StoriesReport.builder()
                .projectToken(projectToken)
                .runDateTime(dateTime)
                .validatedBacklog(validatedBacklog)
                .summary(new SummaryBuilder().build(validatedBacklog))
                .runConfig(validationConfig)
                .build();

        try {
            System.out.println(mapper.writeValueAsString(storiesReport));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        EnterpriseDashboardReporter enterpriseDashboardReporter = new EnterpriseDashboardReporter(new StandaloneLogger(), reportConfig);
        enterpriseDashboardReporter.report(storiesReport);
    }
}
