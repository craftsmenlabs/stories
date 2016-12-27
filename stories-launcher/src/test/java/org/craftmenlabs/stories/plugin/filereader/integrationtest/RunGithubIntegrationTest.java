package org.craftmenlabs.stories.plugin.filereader.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.plugin.filereader.BootApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;
import mockit.*;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class RunGithubIntegrationTest
{
    @Mocked
    private RestTemplate restTemplate;

    @Test
    public void githubTest() throws Exception {
        new Expectations() {{
            restTemplate.getForObject(withAny(""), withAny(String.class));
            result = readFile("github-test.json");

        }};
        SpringApplication application = new SpringApplication(BootApp.class);
        application.setBannerMode(Banner.Mode.OFF);
        ApplicationContext context = application.run("--spring.profiles.active=github-integrationtest");

        BootApp app = context.getBean(BootApp.class);


        Rating rating = app.startApplication();
        assertThat(rating).isEqualTo(Rating.SUCCESS);
    }


    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), Charset.defaultCharset());
    }
}
