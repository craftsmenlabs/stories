package org.craftsmenlabs.stories.importer;

import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Importer
 */
public class JiraAPIImporter implements Importer {
    private final StorynatorLogger logger;

    private String urlResource;
    private String projectKey;
    private String username;
    private String password;

    private JiraJsonParser parser;

    public JiraAPIImporter(StorynatorLogger logger, StorynatorConfig storynatorConfig) {
        this.logger = logger;
        SourceConfig.JiraConfig jiraConfig = storynatorConfig.getSource().getJira();
        this.urlResource = jiraConfig.getUrl();
        this.projectKey = jiraConfig.getProjectKey();
        this.username = jiraConfig.getUsername();
        this.password = jiraConfig.getPassword();

        this.parser = new JiraJsonParser(
                logger,
                storynatorConfig.getFieldMapping(),
                storynatorConfig.getFilter(),
                storynatorConfig.getSource()
        );
    }

    @Override
    public Backlog getBacklog() {

        // build URL params
        String url = urlResource + "/rest/api/2/search";
        logger.info("Retrieving data from: " + url);

        RestTemplate restTemplate = new RestTemplate();

        JiraRequest jiraRequest = JiraRequest.builder()
                .jql("project=" + projectKey)
                .maxResults(10000)
                .build();
        try {
            // Add auth token
            restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
                request.getHeaders().add("Authorization", "Basic " + Base64Utils.encodeToString((this.username + ":" + this.password).getBytes()));
                return execution.execute(request, body);
            }));

            JiraBacklog backlog = restTemplate.postForObject(url, jiraRequest, JiraBacklog.class);
            return parser.parse(backlog);
        } catch (HttpClientErrorException e) {
            logger.error("Jira call went wrong with url: " + urlResource + " and body: " + jiraRequest);
            throw new StoriesException("Failed to connect to " + url + " Error message was: " + e.getMessage() + "body: \r\n" + e.getResponseBodyAsString());
        }
    }
}
