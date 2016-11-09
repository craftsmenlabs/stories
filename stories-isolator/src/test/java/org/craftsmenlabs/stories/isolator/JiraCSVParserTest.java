package org.craftsmenlabs.stories.isolator;

import org.craftsmenlabs.stories.isolator.model.jira.JiraCSVIssueDTO;
import org.craftsmenlabs.stories.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JiraCSVParserTest {

    private String testData = RetrieveTestData.getExportedTestDataFromResource();
    private List<JiraCSVIssueDTO> jiraIssues = RetrieveTestData.getExportedCSVTestResultFromResource();

    @Ignore("Test is ignored, because of deprication")
    @Test
    public void getIssuesTest(){
        JiraCSVParser jiraCSVParser = new JiraCSVParser();
        assertEquals(jiraIssues, jiraCSVParser.getIssues(testData));
    }
}