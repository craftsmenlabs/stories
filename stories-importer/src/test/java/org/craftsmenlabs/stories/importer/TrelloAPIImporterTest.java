package org.craftsmenlabs.stories.importer;

import mockit.Expectations;
import mockit.Mocked;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class TrelloAPIImporterTest {
    private TrelloAPIImporter trelloAPIImporter = new TrelloAPIImporter(new StandaloneLogger());
    private StorynatorConfig config = StorynatorConfig.builder().source(SourceConfig.builder().trello(SourceConfig.TrelloConfig.builder().authKey("key").token("token").projectKey("key").build()).build()).build();

    @Mocked
    private RestTemplate restTemplate;

    @Test
    public void testSuccessResponse() throws Exception {
        new Expectations() {{
            restTemplate.getForObject(withAny(""), withAny(String.class));
            result = readFile("trello-test.json");

        }};

        Backlog backlog = trelloAPIImporter.getBacklog(config);
        assertThat(backlog.getIssues()).hasSize(6);
    }

    @Test(expected = StoriesException.class)
    public void testErrorResponse() throws Exception {
        new Expectations() {{
            restTemplate.getForObject(withAny(""), withAny(String.class));
            result = new StoriesException("");

        }};

        trelloAPIImporter.getBacklog(config);
    }

    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
