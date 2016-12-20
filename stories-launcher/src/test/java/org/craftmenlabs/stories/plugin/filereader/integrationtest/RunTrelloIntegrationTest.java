package org.craftmenlabs.stories.plugin.filereader.integrationtest;


import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.importer.JiraRequest;
import org.craftsmenlabs.stories.plugin.filereader.BootApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class RunTrelloIntegrationTest {
    @Mocked
    private RestTemplate restTemplate;
    @Injectable
    private JiraRequest jiraRequest;

    @Test
    public void trelloTest() throws Exception {
        new Expectations() {{
            restTemplate.getForObject(withAny(""), withAny(String.class));
            result = readFile("trello-test.json");

        }};
        SpringApplication application = new SpringApplication(BootApp.class);
        application.setBannerMode(Banner.Mode.OFF);
        ApplicationContext context = application.run("--spring.profiles.active=trello-integrationtest");

        BootApp app = context.getBean(BootApp.class);


        Rating rating = app.startApplication();
        assertThat(rating).isEqualTo(Rating.SUCCESS);
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()));
    }
}
