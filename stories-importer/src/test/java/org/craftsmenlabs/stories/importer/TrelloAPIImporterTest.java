package org.craftsmenlabs.stories.importer;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class TrelloAPIImporterTest
{
	private TrelloAPIImporter trelloAPIImporter = new TrelloAPIImporter("http://foo.bar", "key", "authKey", "token");

	@Mocked
	private RestTemplate restTemplate;

	@Test
	public void testSuccessResponse() throws Exception {
		new Expectations(){{
			restTemplate.getForObject(withAny(""), withAny(String.class));
			result = readFile("trello.json");

		}};

		List<Issue> issues = trelloAPIImporter.getIssues();
		assertThat(issues).hasSize(6);
	}

	@Test(expected = StoriesException.class)
	public void testErrorResponse() throws Exception {
		new Expectations(){{
			restTemplate.getForObject(withAny(""), withAny(String.class));
			result = new StoriesException("");

		}};

		trelloAPIImporter.getIssues();
	}

	private String readFile(String resource) throws Exception {
		URL url = this.getClass().getClassLoader().getResource(resource);
		return FileUtils.readFileToString(new File(url.toURI()));
	}
}
