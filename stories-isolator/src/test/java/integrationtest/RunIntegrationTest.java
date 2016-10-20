package integrationtest;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
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
        Issue testResult = RetrieveTestData.getTestIssueFromResource();

        List<Issue> issues = jiraCSVParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
        assertEquals(testResult, issues.get(1));
    }


    @Test
    public void runIntegrationTestOnJiraJson(){
        JiraJsonParser jiraJsonParser = new JiraJsonParser();
        String testData = RetrieveTestData.getExportedJiraJSONTestResultFromResource();
        Issue testResult = RetrieveTestData.getTestIssueFromResource();

        List<Issue> issues = jiraJsonParser.getIssues(testData);

        assertEquals(testResult, issues.get(0));
    }

}
