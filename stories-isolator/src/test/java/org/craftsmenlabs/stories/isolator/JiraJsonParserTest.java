package org.craftsmenlabs.stories.isolator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.junit.Assert.*;

public class JiraJsonParserTest {
    private ObjectMapper mapper = new ObjectMapper();
    private FieldMappingConfig fieldMappingConfigCopy =
            FieldMappingConfig.builder()
                    .rank("customfield_11400")
                    .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                    .feature(FieldMappingConfig.FeatureMapping.builder().estimation("customfield_11401").acceptanceCriteria("customfield_10502").build())
                    .bug(FieldMappingConfig.BugMapping.builder().acceptationCriteria("customfield_11404").expectedBehavior("customfield_11405").reproductionPath("customfield_11406").environment("customfield_11407").build())
                    .epic(FieldMappingConfig.EpicMapping.builder().goal("customfield_11404").build())
                    .build();

    private FilterConfig filterConfig = FilterConfig.builder()
            .status("To Do")
            .build();

    private SourceConfig sourceConfig = SourceConfig.builder()
            .jira(SourceConfig.JiraConfig.builder()
                    .url("http://jira.x.nl")
                    .projectKey("testKey")
                    .build()
            ).build();

    private JiraJsonParser jiraJsonParser = new JiraJsonParser(new StandaloneLogger(), fieldMappingConfigCopy, filterConfig, sourceConfig);


    @Test
    public void testGetJiraJsonIssuesReturnsNullOnNull() {
        assertNull(jiraJsonParser.parse(null));
    }

    @Test(expected = StoriesException.class)
    public void testGetJiraJsonIssuesThrowsExceptionWithoutRank() throws Exception {
        String json = readFile("jira-one-story-without-rank-test.json");
        jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
    }

    @Test
    public void testGetJiraJSonIssuesExtractsAcceptanceCriteriaCorrectly() throws Exception {
        String json = readFile("jira-one-story-with-acceptance-criteria-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(backlog.getIssues().size(), 1);
        assertNotNull(((Feature) backlog.getIssues().get("DIU-726")).getAcceptanceCriteria());
        assertThat(((Feature) backlog.getIssues().get("DIU-726")).getAcceptanceCriteria()).isEqualTo("Given I want to configure a profiling rule involving systems\r\n" +
                "When I start typing and stop for 100 ms\r\n" +
                "Then the system provides me with results that match my search string");
    }

    @Test
    public void testGetJiraJsonissuesExtractsEstimationCorrectly() throws Exception {

        String json = readFile("jira-one-story-with-estimation-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(backlog.getIssues().size(), 1);
        Feature feature = (Feature) backlog.getIssues().get("DIU-726");
        assertNotNull(feature.getEstimation());
        assertThat(feature.getEstimation()).isCloseTo(4.56, withinPercentage(1.0));
    }

    @Test
    public void testGetJiraJsonissuesExtractsEstimationNullOnIncorrectFormat() throws Exception {

        String json = readFile("jira-one-story-with-invalid-estimation-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(backlog.getIssues().size(), 1);
        assertThat(((Feature) backlog.getIssues().get("DIU-726")).getEstimation()).isNull();
    }

    @Test
    public void testGetJiraJsonIssuesExtractsEpicsCorrectly() throws Exception {

        String json = readFile("jira-one-epic-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));

        assertEquals(backlog.getIssues().size(), 1);
        Epic epic = (Epic) backlog.getIssues().get("EPIC-1");
        assertThat(epic.getRank()).isNotNull().isEqualToIgnoringCase("0|zgby24:");
        assertThat(epic.getGoal()).isNotNull().isEqualToIgnoringCase("goal");
        assertThat(epic.getSummary()).isNotNull().isEqualToIgnoringCase("Search component for systems");
        assertThat(epic.getKey()).isNotNull().isEqualToIgnoringCase("EPIC-1");
    }

    @Test
    public void testGetjirajsonIssuesExtractsBugsCorrectly() throws Exception {
        String json = readFile("jira-one-bug-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(backlog.getIssues().size(), 1);

        assertNotNull(backlog.getIssues().get("DIU-726"));
        Bug bug = (Bug) backlog.getIssues().get("DIU-726");
        assertNotNull(bug.getAcceptationCriteria());
        assertNotNull(bug.getDescription());
        assertNotNull(bug.getRank());
        assertNotNull(bug.getReproductionPath());
        assertNotNull(bug.getExpectedBehavior());
        assertNotNull(bug.getEnvironment());
        assertNotNull(bug.getPriority());

        assertEquals("Software", bug.getEnvironment());
        assertEquals("High", bug.getPriority());
        assertEquals("Reproduction", bug.getReproductionPath());
        assertEquals("Expected", bug.getExpectedBehavior());
        assertEquals("Acceptation Criteria", bug.getAcceptationCriteria());
    }

    @Test
    public void testGetJiraJsonIssuesReturnsListOnValidString() throws Exception {
        String json = readFile("jira-one-story-with-acceptance-criteria-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));

        assertThat(backlog.getIssues()).hasSize(1);
        assertThat(backlog.getIssues().get("DIU-726").getRank()).isEqualTo("0|zgby24:");
        assertThat(((Feature) backlog.getIssues().get("DIU-726")).getUserstory()).isEqualTo(
                "As a system administrator I want an auto-complete function that provide me with matching system results so that I can handle large datacenters\n" +
                        "\n" +
                        "*scope*\n" +
                        "* A reusable UI component that can be used in all locations where we want a system administrator to manage datacenters\n" +
                        "* The component should have an input box, and dropdown box that shows when you type at least 2 characters\n" +
                        "* The dropdown box shows all the systems for which the description starts with the entered characters (case insensitive)"
                        );

    }

    @Test
    public void testGetJiraJsonissuesBuildsCorrectIssueURL() throws Exception {

        String json = readFile("jira-one-story-with-acceptance-criteria-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));

        assertThat(backlog.getIssues().get("DIU-726").getExternalURI()).isEqualTo("http://jira.x.nl/projects/testKey/issues/DIU-726");
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
