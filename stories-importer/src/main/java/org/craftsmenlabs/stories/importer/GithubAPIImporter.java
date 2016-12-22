package org.craftsmenlabs.stories.importer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.isolator.model.github.GithubJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.GithubJsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Importer
 */
public class GithubAPIImporter implements Importer
{
	private final Logger logger = LoggerFactory.getLogger(GithubAPIImporter.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private String urlResource;
	private String projectKey;
	private String authKey;
	private String token;

	private GithubJsonParser parser;

	public GithubAPIImporter(String urlResource, String projectKey, String authKey, String token)
	{
		this.urlResource = urlResource;
		this.projectKey = projectKey;
		this.authKey = authKey;
		this.token = token;

		this.parser = new GithubJsonParser();
	}

	@Override
	public Backlog getBacklog()
	{
		try
		{
			String url = urlResource + "/repos/" + httpEncode(authKey) + "/" + httpEncode(projectKey) + "/issues?filter=all";
			logger.info("Retrieving data from:" + url);

			try {
				RestTemplate restTemplate = new RestTemplate();
				AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor("token " + httpEncode(token));
				restTemplate.setInterceptors(Collections.singletonList(authorizationInterceptor));
				String responseEntity = restTemplate.getForObject(url, String.class);
				List<GithubJsonIssue> issues = objectMapper.readValue(responseEntity, objectMapper.getTypeFactory().constructCollectionType(List.class, GithubJsonIssue.class));
				return parser.parse(issues);
			} catch (HttpClientErrorException e) {
				logger.error(e.getMessage(), e);
				throw new StoriesException("Failed to connect to " + url);
			}
		}
		catch (IOException e)
		{
			logger.debug(e.getMessage(), e);
			throw new StoriesException("Failed to import GitHub issues: " + e.getMessage());
		}
	}

	private String httpEncode(String toEncode) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(toEncode, "UTF-8");
	}
}

class AuthorizationInterceptor implements ClientHttpRequestInterceptor
{
	private String token;

	public AuthorizationInterceptor(final String auth)
	{
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
