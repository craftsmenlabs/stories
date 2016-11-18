package org.craftsmenlabs.stories.isolator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JiraJsonParserTest {
    private ObjectMapper mapper = new ObjectMapper();
    private FieldMappingConfig fieldMappingConfigCopy =
            FieldMappingConfig.builder()
                    .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                    .issue(FieldMappingConfig.IssueMapping.builder().rank("customfield_11400").acceptenceCriteria("customfield_10502").build())
                    .story(FieldMappingConfig.StoryMapping.builder().build())
                    .criteria(FieldMappingConfig.CriteriaMapping.builder().build())
                    .estimation(FieldMappingConfig.EstimationMapping.builder().build())
                    .build();

    private FilterConfig filterConfig = FilterConfig.builder()
            .status("To Do")
            .build();

    private JiraJsonParser jiraJsonParser = new JiraJsonParser(fieldMappingConfigCopy, filterConfig);


    @Test
    public void testGetJiraJsonIssuesReturnsNullOnNull() {
        assertNull(jiraJsonParser.parse(null));
    }

    @Test
    public void testGetJiraJSonIssuesExtractsAcceptanceCriteriaCorrectly() throws Exception {
        String json = RetrieveTestData.BACKLOG_WITH_ONE_ISSUE_WITH_ACCEPTANCE_CRITERIA_FIELD;
        List<Issue> issues = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));
        assertEquals(issues.size(), 1);
        assertNotNull(issues.get(0).getAcceptanceCriteria());
        assertEquals("Given I want to configure a profiling rule involving systems\r\n" +
                "When I start typing and stop for 100 ms\r\n" +
                "Then the system provides me with results that match my search string", issues.get(0).getAcceptanceCriteria());
    }

    @Test
    public void testGetJiraJsonIssuesReturnsListOnValidString() throws Exception {
        String json = RetrieveTestData.BACKLOG_WITH_ONE_ISSUE_WITH_ACCEPTANCE_CRITERIA_FIELD;
        List<Issue> issues = jiraJsonParser.parse(mapper.readValue(json, JiraBacklog.class));

        assertEquals(issues.size(), 1);
        assertEquals("0|zgby24:",
                issues.get(0).getRank());
        assertEquals("As a system administrator I want an auto-complete function that provide me with matching system results so that I can handle large datacenters\n" +
                        "\n" +
                        "*scope*\n" +
                        "* A reusable UI component that can be used in all locations where we want a system administrator to manage datacenters\n" +
                        "* The component should have an input box, and dropdown box that shows when you type at least 2 characters\n" +
                        "* The dropdown box shows all the systems for which the description starts with the entered characters (case insensitive)",
                issues.get(0).getUserstory());

    }
}
