package org.craftsmenlabs.stories.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Importer
 */
public class TrelloAPIImporter implements Importer {
    private final StorynatorLogger logger;

    private ObjectMapper objectMapper = new ObjectMapper();

    private TrelloJsonParser parser;

    public TrelloAPIImporter(StorynatorLogger logger) {
        this.logger = logger;
        this.parser = new TrelloJsonParser();
    }

    @Override
    public Backlog getBacklog(StorynatorConfig config) {
        SourceConfig.TrelloConfig trelloConfig = config.getSource().getTrello();
        try {

            String url = "https://api.trello.com/1/boards/" + httpEncode(trelloConfig.getProjectKey()) + "/cards?" + "key=" + httpEncode(trelloConfig.getAuthKey()) + "&" + "token=" + httpEncode(trelloConfig.getToken());
            logger.info("Retrieving data from:" + url);

            try {
                RestTemplate restTemplate = new RestTemplate();
                String responseEntity = restTemplate.getForObject(url, String.class);
                List<TrelloJsonIssue> issues = objectMapper.readValue(responseEntity, objectMapper.getTypeFactory().constructCollectionType(List.class, TrelloJsonIssue.class));
                return parser.parse(issues);
            } catch (HttpClientErrorException e) {
                throw new StoriesException("Failed to connect to " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StoriesException("Failed to import Trello issues: " + e.getMessage());
        }
    }

    private String httpEncode(String toEncode) throws UnsupportedEncodingException {
        return URLEncoder.encode(toEncode, "UTF-8");
    }
}
