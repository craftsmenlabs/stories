package org.craftsmenlabs.stories.importer.jira;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class JiraAPIImporterTest {
    private FieldMappingConfig fieldMappingConfigCopy =
            FieldMappingConfig.builder()
                    .rank("Rank")
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

    @Mocked
    private JiraFieldMapRetriever jiraFieldMapRetriever;

    @Test
    public void testSuccessResponse(@Injectable JiraRequest jiraRequest) throws Exception {

        new Expectations() {{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = objectMapper.readValue(readFile("jira-test.json"), JiraBacklog.class);

            jiraFieldMapRetriever.getFieldMap();
            result = new HashMap<String, String>(){{
                put("Rank","customfield_10401");
            }};
        }};

        jiraAPIImporter = new JiraAPIImporter(
                new StandaloneLogger(),
                StorynatorConfig.builder()
                        .fieldMapping(fieldMappingConfigCopy)
                        .filter(filterConfig)
                        .source(SourceConfig.builder()
                                .jira(SourceConfig.JiraConfig.builder().url("http://jira.com").password("").username("").projectKey("HAR").build())
                                .build()).build());
        Backlog backlog = jiraAPIImporter.getBacklog();
        assertThat(backlog.getIssues()).hasSize(1);
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
    public void testMapToJiraIdsReturnsMappingConfig() throws IOException {
        final FieldMappingConfig input = FieldMappingConfig.builder()
                .feature(FieldMappingConfig.FeatureMapping.builder()
                        .estimation("estimationHuman")
                        .acceptanceCriteria("acceptanceCriteriaHuman")
                        .build())
                .bug(FieldMappingConfig.BugMapping.builder()
                        .priority("priority1")
                        .reproductionPath("reproductionPath1")
                        .software("software1")
                        .expectedBehavior("expectedBehavior1")
                        .acceptationCriteria("acceptanceCriteriaHuman")
                        .build())
                .epic(new FieldMappingConfig.EpicMapping("goal1"))
                .teamTask(FieldMappingConfig.TeamTaskMapping.builder()
                        .estimation("estimation1")
                        .acceptanceCriteria("acceptanceCriteriaHuman")
                        .build())
                .rank("Rank")
                .build();

        Map<String, String> fieldMap = new HashMap<String, String>(){{
            put("estimationHuman", "estimation1");
            put("acceptanceCriteriaHuman", "acceptanceCriteria1");
            put("priorityHuman", "priority1");
            put("reproductionPathHuman", "reproductionPath1");
            put("softwareHuman", "software1");
            put("expectedBehaviorHuman", "expectedBehavior1");
            put("goalHuman", "goal1");
            put("estimationHuman", "estimation1");
            put("Rank", "rank1");
        }};

        final FieldMappingConfig actual = jiraAPIImporter.mapToJiraIds(input, fieldMap);

        assertThat(actual.getFeature().getEstimation()).isEqualTo("estimation1");
        assertThat(actual.getFeature().getAcceptanceCriteria()).isEqualTo("acceptanceCriteria1");
        assertThat(actual.getBug().getPriority()).isEqualTo("priority1");
        assertThat(actual.getBug().getReproductionPath()).isEqualTo("reproductionPath1");
        assertThat(actual.getBug().getSoftware()).isEqualTo("software1");
        assertThat(actual.getBug().getExpectedBehavior()).isEqualTo("expectedBehavior1");
        assertThat(actual.getBug().getAcceptationCriteria()).isEqualTo("acceptanceCriteria1");
        assertThat(actual.getEpic().getGoal()).isEqualTo("goal1");
        assertThat(actual.getTeamTask().getEstimation()).isEqualTo("estimation1");
        assertThat(actual.getTeamTask().getAcceptanceCriteria()).isEqualTo("acceptanceCriteria1");
        assertThat(actual.getRank()).isEqualTo("rank1");
    }

    @Test
    public void testMapToJiraIdsWithFileReturnsMappingConfig() throws Exception {
        //load human readable fieldmap
        String fieldMapperString = readFile("jira-fieldMap-test.json");
        ObjectMapper objectMapper = new ObjectMapper();
        final CollectionType valueType = objectMapper.getTypeFactory().constructCollectionType(List.class, JiraFieldMap.class);
        List<JiraFieldMap> jiraFieldMaps = objectMapper.readValue(fieldMapperString, valueType);
        final Map<String, String> humanReadablefieldMap = jiraFieldMaps.stream().collect(Collectors.toMap(JiraFieldMap::getName, JiraFieldMap::getId));

        //set fieldmappingconfig with human readable fields
        FieldMappingConfig input =
                FieldMappingConfig.builder()
                        .rank("Rank")
                        .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                        .feature(FieldMappingConfig.FeatureMapping.builder()
                                .acceptanceCriteria("Acceptance criteria")
                                .estimation("estimation")
                                .build())
                        .bug(FieldMappingConfig.BugMapping.builder()
                                .acceptationCriteria("Acceptance criteria")
                                .expectedBehavior("Expected behaviour")
                                .priority("priority")
                                .reproductionPath("customfield_114004")
                                .software("customfield_11401")
                                .build())
                        .epic(FieldMappingConfig.EpicMapping.builder()
                                .goal("customfield_114007").build())
                        .teamTask(FieldMappingConfig.TeamTaskMapping.builder()
                                .acceptanceCriteria("Acceptance criteria")
                                .estimation("estimation")
                                .build())
                        .build();

        final FieldMappingConfig output = FieldMappingConfig.builder()
                .feature(FieldMappingConfig.FeatureMapping.builder()
                        .estimation("estimation")
                        .acceptanceCriteria("customfield_12900")
                        .build())
                .bug(FieldMappingConfig.BugMapping.builder()
                        .priority("priority")
                        .reproductionPath("customfield_114004")
                        .software("customfield_11401")
                        .expectedBehavior("customfield_12902")
                        .acceptationCriteria("customfield_12900")
                        .build())
                .epic(new FieldMappingConfig.EpicMapping("customfield_114007"))
                .teamTask(FieldMappingConfig.TeamTaskMapping.builder()
                        .estimation("estimation")
                        .acceptanceCriteria("customfield_12900")
                        .build())
                .rank("customfield_11400")
                .build();

        final FieldMappingConfig actual = jiraAPIImporter.mapToJiraIds(input, humanReadablefieldMap);
        assertThat(actual).isEqualTo(output);
//        assertThat(actual.getFeature().getEstimation()).isEqualTo("estimation1");
//        assertThat(actual.getFeature().getAcceptanceCriteria()).isEqualTo("acceptanceCriteria1");
//        assertThat(actual.getBug().getPriority()).isEqualTo("priority1");
//        assertThat(actual.getBug().getReproductionPath()).isEqualTo("reproductionPath1");
//        assertThat(actual.getBug().getSoftware()).isEqualTo("software1");
//        assertThat(actual.getBug().getExpectedBehavior()).isEqualTo("expectedBehavior1");
//        assertThat(actual.getBug().getAcceptationCriteria()).isEqualTo("acceptationCriteria1");
//        assertThat(actual.getEpic().getGoal()).isEqualTo("goal1");
//        assertThat(actual.getTeamTask().getEstimation()).isEqualTo("estimation1");
//        assertThat(actual.getTeamTask().getAcceptanceCriteria()).isEqualTo("acceptanceCriteria1");
//        assertThat(actual.getRank()).isEqualTo("rank1");
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

        final FieldMappingConfig actual = jiraAPIImporter.mapToJiraIds(input, fieldMap);
        assertThat(actual.getFeature().getEstimation()).isEqualTo("");
        assertThat(actual.getBug().getAcceptationCriteria()).isEqualTo("");
        assertThat(actual.getEpic().getGoal()).isEqualTo("");
        assertThat(actual.getTeamTask().getEstimation()).isEqualTo("");
        assertThat(actual.getRank()).isEqualTo("");

    }
}
