package demo.data.importer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Files;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.plugin.filereader.ApplicationConfig;
import org.craftsmenlabs.stories.plugin.filereader.BootApp;
import org.craftsmenlabs.stories.plugin.filereader.PluginExecutor;
import org.craftsmenlabs.stories.plugin.filereader.ValidationConfig;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.IntSupplier;


//TODO don't put communityConnectivityService on the classpath
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootApp.class})
public class importJiraJson {

    @Autowired
    private PluginExecutor pluginExecutor;
    @Autowired
    private ConnectivityService dashboardConnectivity;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private ValidationConfig validationConfig;

    private ValidationConfigCopy validationConfigCopy;


    @Test
    public void importData() {
        Files.fileNamesIn(applicationConfig.getInputfile(), false)
                .forEach(s -> {
                    System.out.println(s);
                    importFile(new File(s));
                });
    }


    //accept file in the format: projectToken_2016-02-13
    public void importFile(File file) {

        String[] split = file.getName().split("_|\\.");
        String projectToken = split[0];
        String date = split[1];

        LocalDateTime dateTime = LocalDate.parse(date).atStartOfDay();

        validationConfigCopy = validationConfig.clone();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<JiraJsonIssue>> mapType = new TypeReference<List<JiraJsonIssue>>() {
        };
        JiraBacklog jiraBacklog = null;
        try {
            jiraBacklog = mapper.readValue(file, JiraBacklog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Issue> issues = new JiraJsonParser().getIssues(jiraBacklog.getJiraJsonIssues());

        Backlog backlog = new Backlog();
        backlog.setIssues(issues);

        BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfigCopy);

        StoriesRun storiesRun = StoriesRun.builder()
                .projectToken(projectToken)
                .runDateTime(dateTime)
                .backlogValidatorEntry(backlogValidatorEntry)
                .summary(new SummaryBuilder().build(backlogValidatorEntry))
                .runConfig(validationConfigCopy)
                .build();

        System.out.println(storiesRun.getProjectToken() + " @ " + storiesRun.getRunDateTime());

        dashboardConnectivity.sendData(storiesRun);
    }

    public IntSupplier quiz(final int[] vals, int i) {
        if (vals[0] < 0)
            vals[i] = 0;
        return () -> vals[i];
    }

}
