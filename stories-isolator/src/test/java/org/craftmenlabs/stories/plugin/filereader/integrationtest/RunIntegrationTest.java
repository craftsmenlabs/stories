package org.craftmenlabs.stories.plugin.filereader.integrationtest;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.parser.FieldMappingConfigCopy;
import org.craftsmenlabs.stories.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunIntegrationTest {


    @Ignore("csv temporarily unmaintained")
    @Test
    public void runIntegrationTest(){
        JiraCSVParser jiraCSVParser = new JiraCSVParser();

        String testData = RetrieveTestData.getExportedTestDataFromResource();
        Issue testResult = RetrieveTestData.getJiraTestIssueFromResource();

        List<Issue> issues = jiraCSVParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
        assertEquals(testResult, issues.get(1));
    }


    @Test
    public void runIntegrationTestOnJiraJson(){
        FieldMappingConfigCopy fieldMappingConfigCopy =
                FieldMappingConfigCopy.builder()
                        .backlog(FieldMappingConfigCopy.BacklogMappingCopy.builder().build())
                        .issue(FieldMappingConfigCopy.IssueMappingCopy.builder()
                                .rank("customfield_10401")
                                .estimation("customfield_10308")
                                .build())
                        .story(FieldMappingConfigCopy.StoryMappingCopy.builder().build())
                        .criteria(FieldMappingConfigCopy.CriteriaMappingCopy.builder().build())
                        .estimation(FieldMappingConfigCopy.EstimationMappingCopy.builder().build())
                        .build();

        JiraJsonParser jiraJsonParser = new JiraJsonParser(fieldMappingConfigCopy);
        String testData = RetrieveTestData.getExportedJiraJSONTestResultFromResource();
        Issue testResult = RetrieveTestData.getJiraTestIssueFromResource();

        List<Issue> issues = jiraJsonParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
    }

    @Test
    public void runIntegrationTestOnTrelloJson(){
        TrelloJsonParser trelloJsonParser = new TrelloJsonParser();
        String testData = RetrieveTestData.getExportedTrelloJSONTestResultFromResource();
        Issue testResult = RetrieveTestData.getTrelloTestIssueFromResource();

        List<Issue> issues = trelloJsonParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
    }

}
