package integrationtest;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.parser.*;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Ignore;
import org.junit.Test;

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
        JiraJsonParser jiraJsonParser = new JiraJsonParser();
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
