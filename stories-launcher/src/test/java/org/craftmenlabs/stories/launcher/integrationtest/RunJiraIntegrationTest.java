package org.craftmenlabs.stories.launcher.integrationtest;


import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.importer.jira.JiraRequest;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.launcher.BootApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class RunJiraIntegrationTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mocked
    private RestTemplate restTemplate;
    @Injectable
    private JiraRequest jiraRequest;

    @Test
    public void jiraTest() throws Exception {
        new Expectations() {{
            restTemplate.postForObject(withAny(""), withAny(jiraRequest), withAny(JiraBacklog.class));
            result = objectMapper.readValue(readFile("jira-test.json"), JiraBacklog.class);

        }};
        SpringApplication application = new SpringApplication(BootApp.class);
        application.setBannerMode(Banner.Mode.OFF);
        ApplicationContext context = application.run("--spring.profiles.active=jira-integrationtest");

        BootApp app = context.getBean(BootApp.class);


        Rating rating = app.startApplication();
        assertThat(rating).isEqualTo(Rating.SUCCESS);
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), Charset.defaultCharset());
    }
}
