package org.craftsmenlabs.stories.importer;

import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Importer
 */
public class JiraAPIImporter implements Importer
{
	private final Logger logger = LoggerFactory.getLogger(JiraAPIImporter.class);

	private String urlResource;
	private String projectKey;
	private String authKey;

	private FilterConfig filterConfig;
	private JiraJsonParser parser;

	public JiraAPIImporter(String urlResource, String projectKey, String authKey, FieldMappingConfig fieldMappingConfig, FilterConfig filterConfig)
	{
		this.urlResource = urlResource;
		this.projectKey = projectKey;
		this.authKey = authKey;
		this.filterConfig = filterConfig;

		this.parser = new JiraJsonParser(fieldMappingConfig, filterConfig);
	}

	@Override
	public Backlog getBacklog()
	{

		// build URL params
		String url = urlResource + "/rest/api/2/search";
		logger.info("Retrieving data from: " + url);

		try {
			RestTemplate restTemplate = new RestTemplate();

			// Add auth token
			restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
				request.getHeaders().add("Authorization", "Basic " + authKey);
				return execution.execute(request, body);
			}));

			JiraRequest request = JiraRequest.builder()
					.jql("project=" + projectKey + " AND status=\"" + filterConfig.getStatus() + "\"")
					.maxResults(10000)
					.build();

			JiraBacklog backlog = restTemplate.postForObject(url, request, JiraBacklog.class);
			return parser.parse(backlog);
		} catch (HttpClientErrorException e) {
			throw new StoriesException("Failed to connect to " + url + " Error message was: " + e.getMessage() + "body: \r\n" + e.getResponseBodyAsString());
		}
	}
}
