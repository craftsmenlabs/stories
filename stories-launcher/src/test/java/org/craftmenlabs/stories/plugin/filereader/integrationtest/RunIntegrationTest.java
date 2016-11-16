package org.craftmenlabs.stories.plugin.filereader.integrationtest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.FieldMappingConfigCopy;
import org.craftsmenlabs.stories.plugin.filereader.config.SpringSourceConfig;
import org.craftsmenlabs.stories.plugin.filereader.BootApp;
import org.craftsmenlabs.stories.plugin.filereader.config.SpringFieldMappingConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.SpringValidationConfig;
import org.junit.Before;
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
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootApp.class})
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
public class RunIntegrationTest {

    @Autowired
    private ConnectivityService dashboardConnectivity;
    @Autowired
    private SpringSourceConfig springSourceConfig;
    @Autowired
    private SpringValidationConfig springValidationConfig;
    @Autowired
    private SpringFieldMappingConfig springFieldMappingConfig;

    private ValidationConfig validationConfig;
    private FieldMappingConfig fieldMappingConfigCopy;

    private JiraBacklog jiraBacklog;

    private ValidationConfig buildValidatorEntryCopy() {
        ValidationConfig.ValidatorEntry backlog = new ValidationConfig.ValidatorEntry();
        backlog.setRatingtreshold(60f);

        ValidationConfig.ValidatorEntry issue = new ValidationConfig.ValidatorEntry();
        issue.setRatingtreshold(0.7f);

        ValidationConfig.StoryValidatorEntry story = new ValidationConfig.StoryValidatorEntry();
        story.setRatingtreshold(0.3f);
        story.setActive(true);
        story.setAsKeywords(Arrays.asList("as a"));
        story.setIKeywords(Arrays.asList("i want to"));
        story.setSoKeywords(Arrays.asList("so "));

        ValidationConfig.CriteriaValidatorEntry criteria = new ValidationConfig.CriteriaValidatorEntry();
        criteria.setRatingtreshold(0.4f);
        criteria.setActive(true);
        criteria.setGivenKeywords(Arrays.asList("given"));
        criteria.setWhenKeywords(Arrays.asList("when"));
        criteria.setThenKeywords(Arrays.asList("then"));

        ValidationConfig.ValidatorEntry estimation = new ValidationConfig.ValidatorEntry();
        estimation.setActive(false);
        estimation.setRatingtreshold(0.7f);

        return ValidationConfig.builder()
                .backlog(backlog)
                .issue(issue)
                .story(story)
                .criteria(criteria)
                .estimation(estimation)
                .build();
    }

    private FieldMappingConfigCopy buildFieldMappingConfigCopy() {
        return FieldMappingConfigCopy.builder()
                .issue(FieldMappingConfigCopy.IssueMappingCopy.builder()
                        .rank("customfield_11400")
                        .estimation("customfield_10401")
                        .build())
                .build();
    }

    private JiraBacklog buildJiraBacklog() {
        return null;
    }

    @Before
    public void importFileTest() {
        validationConfig = springValidationConfig.convert();
        fieldMappingConfigCopy = springFieldMappingConfig.convert();

        File file = new File(getClass().getClassLoader().getResource("issuesfile.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        try {
            jiraBacklog = mapper.readValue(file, JiraBacklog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        List<Issue> issues = new JiraJsonParser(fieldMappingConfig).getIssues(jiraBacklog.getJiraJsonIssues());

//        Backlog backlog = new Backlog();
//        backlog.setIssues(issues);
//
//        BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfig);
//
//        StoriesRun storiesRun = StoriesRun.builder()
//                .backlogValidatorEntry(backlogValidatorEntry)
//                .summary(new SummaryBuilder().build(backlogValidatorEntry))
//                .runConfig(validationConfig)
//                .build();

    }


    @Ignore
    @Test
    public void jiraBacklogTest() {
        assertThat(jiraBacklog.getJiraJsonIssues().size()).isEqualTo(7);
        assertThat(jiraBacklog.getJiraJsonIssues().stream()
                .map(JiraJsonIssue::getKey).collect(Collectors.toList()))
                .containsExactly(
                        "Stories-512",
                        "Stories-511",
                        "Stories-510",
                        "Stories-509",
                        "Stories-508",
                        "Stories-507",
                        "Stories-506");
    }

    @Ignore
    @Test
    public void loadConfigTest() {
        assertThat(this.buildValidatorEntryCopy()).isEqualTo(validationConfig);
        assertThat(this.buildFieldMappingConfigCopy()).isEqualTo(fieldMappingConfigCopy);
    }
}
