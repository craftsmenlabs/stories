package org.craftsmenlabs.stories.isolator;

import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;

import static org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData.BACKLOG_WITH_ONE_ISSUE;
import static org.junit.Assert.*;

public class JiraJsonParserTest {
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
        assertNull(jiraJsonParser.getJiraJsonIssues(null));
    }

    @Test
    public void testGetJiraJSonIssuesExtractsAcceptanceCriteriaCorrectly() {
        String json = RetrieveTestData.BACKLOG_WITH_ONE_ISSUE_WITH_ACCEPTANCE_CRITERIA_FIELD;
        List<Issue> issues = jiraJsonParser.getIssues(json);
        assertEquals(issues.size(), 1);
        assertNotNull(issues.get(0).getAcceptanceCriteria());
        assertEquals("Given I want to configure a profiling rule involving systems\r\n" +
                "When I start typing and stop for 100 ms\r\n" +
                "Then the system provides me with results that match my search string", issues.get(0).getAcceptanceCriteria());
    }

    @Test
    public void testGetJiraJsonIssuesReturnsListOnValidString() {
        List<JiraJsonIssue> results = jiraJsonParser.getJiraJsonIssues(BACKLOG_WITH_ONE_ISSUE);

        assertEquals(results.size(), 1);
        assertEquals(
                results.get(0).getFields().getAdditionalPropertiesAsString().get(
                        fieldMappingConfigCopy.getIssue().getRank())
                , "2|i02txb:");
        assertEquals(
                results.get(0).getFields().getIssuetype().getName()
                , "Story");

    }

    @Ignore("Test to be implemented")
    @Test
    public void getJiraJsonIssuesTest() {
        File file = new File("/Users/youritjang/IdeaProjects/stories/temp/issuesfile.json");
        try {
            String input = FileUtils.readFileToString(file, Charset.defaultCharset());
            List<JiraJsonIssue> jiraJsonIssues = jiraJsonParser.getJiraJsonIssues(input);

            jiraJsonIssues.stream()
                    .filter(jiraJsonIssue -> jiraJsonIssue.getFields().getIssuetype().name.equals("Story"))
                    .forEach(jiraJsonIssue -> System.out.println(jiraJsonIssue));

            //TODO: Fix this test
            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Ignore("Test to be implemented")
    @Test
    public void getIssuesTest() {
        File file = new File("/Users/youritjang/IdeaProjects/stories/temp/issuesfile.json");
        try {
            String input = FileUtils.readFileToString(file, Charset.defaultCharset());

            jiraJsonParser.getIssues(input)
                    .stream()
                    .sorted(Comparator.comparing(Issue::getRank))
                    .filter(issue -> issue.getUserstory() != null && !issue.getUserstory().isEmpty())
                    .forEach(issue -> {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Key= ");
                        sb.append(issue.getKey());
                        sb.append(", ");
                        sb.append("Rank= ");
                        sb.append(issue.getRank());
                        sb.append(", ");
                        sb.append("Est= ");
                        sb.append(issue.getEstimation());
                        sb.append(" ");
                        sb.append("\n");
                        sb.append(issue.getUserstory());
                        System.out.println(sb.toString());
            });

            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
