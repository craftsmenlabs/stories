package org.craftsmenlabs.stories.importer.jira;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class JiraAPIImporterTest {
    private FieldMappingConfig fieldMappingConfigCopy =
            FieldMappingConfig.builder()
                    .rank("customfield_10401")
                    .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                    .feature(FieldMappingConfig.FeatureMapping.builder()
                            .acceptanceCriteria("customfield_10502")
                            .estimation("estimation")
                            .build())
                    .bug(FieldMappingConfig.BugMapping.builder()
                            .acceptationCriteria("customfield_11404")
                            .expectedBehavior("customfield_114005")
                            .priority("priority")
                            .reproductionPath("customfield_114004")
                            .software("customfield_11401")
                            .build())
                    .epic(FieldMappingConfig.EpicMapping.builder()
                            .goal("customfield_114007").build())
                    .teamTask(FieldMappingConfig.TeamTaskMapping.builder()
                            .acceptanceCriteria("acceptance criteria")
                            .estimation("estimation")
                            .build())
                    .build();

    private FilterConfig filterConfig = FilterConfig.builder()
            .status("To Do")
            .build();

    private ObjectMapper objectMapper = new ObjectMapper();

    private JiraAPIImporter jiraAPIImporter = new JiraAPIImporter(new StandaloneLogger(), StorynatorConfig.builder().fieldMapping(fieldMappingConfigCopy).filter(filterConfig).source(SourceConfig.builder().jira(SourceConfig.JiraConfig.builder().url("http://jira.com").password("").username("").projectKey("HAR").build()).build()).build());


    @Mocked
    private RestTemplate restTemplate;

    @Test
    public void testSuccessResponse(@Injectable JiraRequest jiraRequest) throws Exception {
        new Expectations() {{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = objectMapper.readValue(readFile("jira-test.json"), JiraBacklog.class);

        }};

        Backlog backlog = jiraAPIImporter.getBacklog();
        assertThat(backlog.getFeatures()).hasSize(1);
    }

    @Test(expected = StoriesException.class)
    public void testErrorResponse(@Injectable JiraRequest jiraRequest) throws Exception {
        new Expectations() {{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }};


        jiraAPIImporter.getBacklog();
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }

    @Test
    public void testMapToHumanReadableFieldsReturnsMappingConfig() throws IOException {
        final FieldMappingConfig input = FieldMappingConfig.builder()
                .feature(FieldMappingConfig.FeatureMapping.builder()
                        .estimation("estimation1")
                        .acceptanceCriteria("acceptanceCriteria1")
                        .build())
                .bug(FieldMappingConfig.BugMapping.builder()
                        .priority("priority1")
                        .reproductionPath("reproductionPath1")
                        .software("software1")
                        .expectedBehavior("expectedBehavior1")
                        .acceptationCriteria("acceptationCriteria1")
                        .build())
                .epic(new FieldMappingConfig.EpicMapping("goal1"))
                .teamTask(FieldMappingConfig.TeamTaskMapping.builder()
                        .estimation("estimation1")
                        .acceptanceCriteria("acceptanceCriteria1")
                        .build())
                .rank("rank1")
                .build();

        Map<String, String> fieldMap = new HashMap<String, String>(){{
            put("estimationHuman", "estimation1");
        }};

        final FieldMappingConfig actual = jiraAPIImporter.mapToHumanReadableFields(input, fieldMap);

        assertThat(actual.getFeature().getEstimation()).isEqualTo("estimation1");
    }
    @Test
    public void testMapToHumanReadableFieldsReturnsEmptiesOnEmptyConfig() throws IOException {
        final FieldMappingConfig input = FieldMappingConfig.builder()
                .feature(null)
                .bug(null)
                .epic(null)
                .teamTask(null)
                .rank("")
                .build();

        Map<String, String> fieldMap = new HashMap<String, String>(){{
            put("estimationHuman", "estimation1");
        }};

        final FieldMappingConfig actual = jiraAPIImporter.mapToHumanReadableFields(input, fieldMap);
        assertThat(actual.getFeature().getEstimation()).isEqualTo("");
        assertThat(actual.getBug().getAcceptationCriteria()).isEqualTo("");
        assertThat(actual.getEpic().getGoal()).isEqualTo("");
        assertThat(actual.getTeamTask().getEstimation()).isEqualTo("");
        assertThat(actual.getRank()).isEqualTo("");

    }
}