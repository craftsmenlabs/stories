package org.craftsmenlabs.stories.isolator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Bug;
import org.craftsmenlabs.stories.api.models.scrumitems.Epic;
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
                    .bug(FieldMappingConfig.BugMapping.builder().acceptationCriteria("customfield_11404").expectedBehavior("customfield_11405").reproductionPath("customfield_11406").software("customfield_11407").build())
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

    private JiraJsonParser jiraJsonParser = new JiraJsonParser(fieldMappingConfigCopy, filterConfig, sourceConfig);


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
        assertEquals(backlog.getFeatures().size(), 1);
        assertNotNull(backlog.getFeatures().get(0).getAcceptanceCriteria());
        assertEquals("Given I want to configure a profiling rule involving systems\r\n" +
                "When I start typing and stop for 100 ms\r\n" +
                "Then the system provides me with results that match my search string", backlog.getFeatures().get(0).getAcceptanceCriteria());
    }

    @Test
    public void testGetJiraJsonissuesExtractsEstimationCorrectly() throws Exception {

        String json = readFile("jira-one-story-with-estimation-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(backlog.getFeatures().size(), 1);
        assertNotNull(backlog.getFeatures().get(0).getEstimation());
        assertThat(backlog.getFeatures().get(0).getEstimation()).isCloseTo(4.56f, withinPercentage(1.0));
    }

    @Test
    public void testGetJiraJsonissuesExtractsEstimationNullOnIncorrectFormat() throws Exception {

        String json = readFile("jira-one-story-with-invalid-estimation-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(backlog.getFeatures().size(), 1);
        assertThat(backlog.getFeatures().get(0).getEstimation()).isEqualTo(0.0f);
    }

    @Test
    public void testGetJiraJsonIssuesExtractsEpicsCorrectly() throws Exception {

        String json = readFile("jira-one-epic-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));

        assertEquals(backlog.getEpics().size(), 1);
        Epic epic = backlog.getEpics().get(0);
        assertThat(epic.getRank()).isNotNull().isEqualToIgnoringCase("0|zgby24:");
        assertThat(epic.getGoal()).isNotNull().isEqualToIgnoringCase("goal");
        assertThat(epic.getSummary()).isNotNull().isEqualToIgnoringCase("Search component for systems");
        assertThat(epic.getKey()).isNotNull().isEqualToIgnoringCase("EPIC-1");
    }

    @Test
    public void testGetjirajsonIssuesExtractsBugsCorrectly() throws Exception {
        String json = readFile("jira-one-bug-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(backlog.getFeatures().size(), 0);
        assertEquals(backlog.getBugs().size(), 1);

        assertNotNull(backlog.getBugs().get(0));
        Bug bug = backlog.getBugs().get(0);
        assertNotNull(bug.getAcceptationCriteria());
        assertNotNull(bug.getDescription());
        assertNotNull(bug.getRank());
        assertNotNull(bug.getReproductionPath());
        assertNotNull(bug.getExpectedBehavior());
        assertNotNull(bug.getSoftware());
        assertNotNull(bug.getPriority());

        assertEquals("Software", bug.getSoftware());
        assertEquals("High", bug.getPriority());
        assertEquals("Reproduction", bug.getReproductionPath());
        assertEquals("Expected", bug.getExpectedBehavior());
        assertEquals("Acceptation Criteria", bug.getAcceptationCriteria());
    }

    @Test
    public void testGetJiraJsonIssuesReturnsListOnValidString() throws Exception {
        String json = readFile("jira-one-story-with-acceptance-criteria-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));

        assertEquals(backlog.getFeatures().size(), 1);
        assertEquals("0|zgby24:",
                backlog.getFeatures().get(0).getRank());
        assertEquals("As a system administrator I want an auto-complete function that provide me with matching system results so that I can handle large datacenters\n" +
                        "\n" +
                        "*scope*\n" +
                        "* A reusable UI component that can be used in all locations where we want a system administrator to manage datacenters\n" +
                        "* The component should have an input box, and dropdown box that shows when you type at least 2 characters\n" +
                        "* The dropdown box shows all the systems for which the description starts with the entered characters (case insensitive)",
                backlog.getFeatures().get(0).getUserstory());

    }

    @Test
    public void testGetJiraJsonissuesBuildsCorrectIssueURL() throws Exception {

        String json = readFile("jira-one-story-with-acceptance-criteria-test.json");
        Backlog backlog = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));

        assertThat(backlog.getFeatures().get(0).getExternalURI()).isEqualTo("http://jira.x.nl/projects/testKey/issues/DIU-726");
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
