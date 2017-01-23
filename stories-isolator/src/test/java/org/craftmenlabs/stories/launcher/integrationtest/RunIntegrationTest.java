package org.craftmenlabs.stories.launcher.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunIntegrationTest {
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void runIntegrationTestOnJiraJson() throws Exception {
        FieldMappingConfig fieldMappingConfig =
                FieldMappingConfig.builder()
                        .rank("customfield_10401")
                        .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                        .feature(FieldMappingConfig.FeatureMapping.builder().acceptanceCriteria("customfield_10502").build())
                        .bug(FieldMappingConfig.BugMapping.builder().acceptationCriteria("customfield_11404").expectedBehavior("customfield_114005").reproductionPath("customfield_114004").software("customfield_11401").build())
                        .epic(FieldMappingConfig.EpicMapping.builder().goal("customfield_114007").build())
                        .build();

        JiraJsonParser jiraJsonParser =
                new JiraJsonParser(
                        new StandaloneLogger(),
                        fieldMappingConfig,
                        FilterConfig.builder()
                                .status("To Do")
                                .build(),
                        SourceConfig.builder()
                                .jira(SourceConfig.JiraConfig.builder()
                                        .url("http://jira.foo.bar")
                                        .projectKey("EPM")
                                        .build())
                                .build());
        String testData = readFile("jira-integration-test.json");
        Feature testResult = RetrieveTestData.getJiraTestIssueFromResource();

        Backlog backlog = jiraJsonParser.parse(mapper.readValue(testData, JiraBacklog.class));

        assertEquals(testResult, backlog.getIssues().get("EPM-512"));
    }

    @Test
    public void runIntegrationTestOnTrelloJson() throws Exception {
        TrelloJsonParser trelloJsonParser = new TrelloJsonParser();
        String testData = readFile("trello-integration-test.json");
        Feature testResult = RetrieveTestData.getTrelloTestIssueFromResource();

        Backlog backlog = trelloJsonParser.parse(mapper.readValue(testData, mapper.getTypeFactory().constructCollectionType(List.class, TrelloJsonIssue.class)));

        assertEquals(testResult, backlog.getIssues().get("581b199ba7dfd7e8f737262c"));
    }

    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
