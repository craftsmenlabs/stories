package org.craftsmenlabs.stories.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class JiraAPIImporterTest {
    private FieldMappingConfig fieldMappingConfigCopy =
            FieldMappingConfig.builder()
                    .rank("customfield_10401")
                    .backlog(FieldMappingConfig.BacklogMapping.builder().build())
                    .feature(FieldMappingConfig.FeatureMapping.builder().acceptanceCriteria("customfield_10502").build())
                    .bug(FieldMappingConfig.BugMapping.builder().acceptationCriteria("customfield_11404").expectedBehavior("customfield_114005").reproductionPath("customfield_114004").software("customfield_11401").build())
                    .epic(FieldMappingConfig.EpicMapping.builder().goal("customfield_114007").build())
                    .build();

    private FilterConfig filterConfig = FilterConfig.builder()
            .status("To Do")
            .build();

    private ObjectMapper objectMapper = new ObjectMapper();

    private JiraAPIImporter jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "key", fieldMappingConfigCopy, filterConfig);

    @Mocked
    private RestTemplate restTemplate;

    @Test
    public void testSuccessResponse(@Injectable JiraRequest jiraRequest) throws Exception {
        new Expectations(){{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = objectMapper.readValue(readFile("jira-test.json"), JiraBacklog.class);

        }};

        Backlog backlog = jiraAPIImporter.getBacklog();
        assertThat(backlog.getFeatures()).hasSize(1);
    }

    @Test(expected = StoriesException.class)
    public void testErrorResponse(@Injectable JiraRequest jiraRequest) throws Exception {
        new Expectations(){{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }};


        jiraAPIImporter.getBacklog();
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
