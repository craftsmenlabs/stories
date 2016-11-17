package org.craftmenlabs.stories.plugin.filereader.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunIntegrationTest {
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void runIntegrationTestOnJiraJson() throws Exception {
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

        List<Issue> issues = jiraJsonParser.parse(mapper.readValue(testData, JiraBacklog.class));

        assertEquals(testResult, issues.get(0));
    }

    @Test
    public void runIntegrationTestOnTrelloJson() throws Exception {
        TrelloJsonParser trelloJsonParser = new TrelloJsonParser();
        String testData = RetrieveTestData.getExportedTrelloJSONTestResultFromResource();
        Issue testResult = RetrieveTestData.getTrelloTestIssueFromResource();

        List<Issue> issues = trelloJsonParser.parse(mapper.readValue(testData, mapper.getTypeFactory().constructCollectionType(List.class, TrelloJsonIssue.class)));

        assertEquals(testResult, issues.get(0));
    }

}
