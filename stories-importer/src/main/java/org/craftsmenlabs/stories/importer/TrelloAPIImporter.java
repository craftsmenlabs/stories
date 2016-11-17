package org.craftsmenlabs.stories.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.GET;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * Importer
 */
public class TrelloAPIImporter implements Importer
{
	private final Logger logger = LoggerFactory.getLogger(TrelloAPIImporter.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private String urlResource;
	private String projectKey;
	private String authKey;
	private String token;

	private TrelloJsonParser parser;

	public TrelloAPIImporter(String urlResource, String projectKey, String authKey, String token)
	{
		this.urlResource = urlResource;
		this.projectKey = projectKey;
		this.authKey = authKey;
		this.token = token;

		this.parser = new TrelloJsonParser();
	}

	public List<Issue> getIssues()
	{
		try
		{
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
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new StoriesException("Failed to import Trello issues: " + e.getMessage());
		}
	}

	private String httpEncode(String toEncode) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(toEncode, "UTF-8");
	}
}
