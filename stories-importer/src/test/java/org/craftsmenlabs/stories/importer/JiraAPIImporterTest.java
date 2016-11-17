package org.craftsmenlabs.stories.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.Mocked;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class JiraAPIImporterTest {
    private FieldMappingConfig fieldMappingConfigCopy =
            FieldMappingConfig.builder()
                    .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                    .issue(FieldMappingConfig.IssueMapping.builder().rank("customfield_10401").acceptenceCriteria("customfield_10502").build())
                    .story(FieldMappingConfig.StoryMapping.builder().build())
                    .criteria(FieldMappingConfig.CriteriaMapping.builder().build())
                    .estimation(FieldMappingConfig.EstimationMapping.builder().build())
                    .build();

    private FilterConfig filterConfig = FilterConfig.builder()
            .status("To Do")
            .build();

    private ObjectMapper objectMapper = new ObjectMapper();

    private JiraAPIImporter jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "username", "password", fieldMappingConfigCopy, filterConfig);

    @Mocked
    private RestTemplate restTemplate;

    @Test
    public void testSuccessResponse(@Injectable JiraRequest jiraRequest) throws Exception {
        new Expectations(){{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = objectMapper.readValue(readFile("jira.json"), JiraBacklog.class);

        }};

        List<Issue> issues = jiraAPIImporter.getIssues();
        assertThat(issues).hasSize(6);
    }

    @Test(expected = StoriesException.class)
    public void testErrorResponse(@Injectable JiraRequest jiraRequest) throws Exception {
        new Expectations(){{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }};


        jiraAPIImporter.getIssues();
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()));
    }
}
