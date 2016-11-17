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
}
