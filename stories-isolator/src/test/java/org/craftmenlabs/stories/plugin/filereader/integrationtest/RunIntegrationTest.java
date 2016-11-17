package org.craftmenlabs.stories.plugin.filereader.integrationtest;

import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunIntegrationTest {
    @Test
    public void runIntegrationTestOnJiraJson() {
        FieldMappingConfig fieldMappingConfig =
                FieldMappingConfig.builder()
                        .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                        .issue(FieldMappingConfig.IssueMapping.builder()
                                .rank("customfield_10401")
                                .estimation("customfield_10308")
                                .build())
                        .story(FieldMappingConfig.StoryMapping.builder().build())
                        .criteria(FieldMappingConfig.CriteriaMapping.builder().build())
                        .estimation(FieldMappingConfig.EstimationMapping.builder().build())
                        .build();

        JiraJsonParser jiraJsonParser = new JiraJsonParser(fieldMappingConfig, FilterConfig.builder().status("To Do").build());
        String testData = RetrieveTestData.getExportedJiraJSONTestResultFromResource();
        Issue testResult = RetrieveTestData.getJiraTestIssueFromResource();

        List<Issue> issues = jiraJsonParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
    }

    @Test
    public void runIntegrationTestOnTrelloJson() {
        TrelloJsonParser trelloJsonParser = new TrelloJsonParser();
        String testData = RetrieveTestData.getExportedTrelloJSONTestResultFromResource();
        Issue testResult = RetrieveTestData.getTrelloTestIssueFromResource();

        List<Issue> issues = trelloJsonParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
    }

}
