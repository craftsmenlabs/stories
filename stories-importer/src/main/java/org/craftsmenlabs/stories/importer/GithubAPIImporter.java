package org.craftsmenlabs.stories.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.isolator.model.github.GithubJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.GithubJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * Importer
 */
public class GithubAPIImporter implements Importer {
    private final StorynatorLogger logger;

    private ObjectMapper objectMapper = new ObjectMapper();

    private GithubJsonParser parser;

    public GithubAPIImporter(StorynatorLogger logger) {
        this.logger = logger;
        this.parser = new GithubJsonParser();
    }

    @Override
    public Backlog getBacklog(StorynatorConfig config) {
        SourceConfig.GithubConfig githubConfig = config.getSource().getGithub();
        try {
            String url = "http://github.com/repos/" + httpEncode(githubConfig.getOwner()) + "/" + httpEncode(githubConfig.getProject()) + "/issues?filter=all";
            logger.info("Retrieving data from:" + url);

            try {
                RestTemplate restTemplate = new RestTemplate();
                AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor("token " + httpEncode(githubConfig.getToken()));
                restTemplate.setInterceptors(Collections.singletonList(authorizationInterceptor));
                String responseEntity = restTemplate.getForObject(url, String.class);
                List<GithubJsonIssue> issues = objectMapper.readValue(responseEntity, objectMapper.getTypeFactory().constructCollectionType(List.class, GithubJsonIssue.class));
                return parser.parse(issues);
            } catch (HttpClientErrorException e) {
                logger.error(e.getMessage(), e);
                throw new StoriesException("Failed to connect to " + url);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new StoriesException("Failed to import GitHub issues: " + e.getMessage());
        }
    }

    private String httpEncode(String toEncode) throws UnsupportedEncodingException {
        return URLEncoder.encode(toEncode, "UTF-8");
    }
}

class AuthorizationInterceptor implements ClientHttpRequestInterceptor {
    private String token;

    public AuthorizationInterceptor(final String auth) {
        token = auth;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", token);
        return execution.execute(request, body);
    }
}
