package org.craftsmenlabs.stories.importer.jira;

import mockit.Expectations;
import mockit.Mocked;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class JiraFieldMapRetrieverTest {

    JiraFieldMapRetriever jiraFieldMapRetriever;

    @Mocked
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        jiraFieldMapRetriever = new JiraFieldMapRetriever("username", "password", "http://jira.com", new StandaloneLogger());
    }

    @Test
    public void getFieldMap() throws Exception {
        new Expectations() {{
            restTemplate.getForObject(withAny(""), withAny(String.class));
            result = readFile("jira-fieldMap-test.json");
        }};

        assertThat(jiraFieldMapRetriever.getFieldMap().size()).isEqualTo(92);
        assertThat(jiraFieldMapRetriever.getFieldMap().get("Issue Type")).isEqualTo("issuetype");
    }


//    @Test
//    public void getFieldMapThrowsStorynatorException() throws Exception {
//        new Expectations() {{
//            restTemplate.getForObject(withAny(""), withAny(String.class));
//            result = new HttpClientErrorException("");
//        }};
//
//        assertThatExceptionOfType(StoriesException.class).isThrownBy(() -> jiraFieldMapRetriever.getFieldMap());
//    }

    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}