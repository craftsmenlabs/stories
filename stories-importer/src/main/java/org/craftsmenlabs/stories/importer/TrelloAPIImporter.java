package org.craftsmenlabs.stories.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.Backlog;
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

    private String urlResource;
    private String projectKey;
    private String authKey;
    private String token;

    private TrelloJsonParser parser;

    public TrelloAPIImporter(StorynatorLogger logger, String urlResource, String projectKey, String authKey, String token) {
        this.logger = logger;
        this.urlResource = urlResource;
        this.projectKey = projectKey;
        this.authKey = authKey;
        this.token = token;

        this.parser = new TrelloJsonParser();
    }

    @Override
    public Backlog getBacklog() {
        try {
            String url = urlResource + "/boards/" + httpEncode(projectKey) + "/cards?" + "key=" + httpEncode(authKey) + "&" + "token=" + httpEncode(token);
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
