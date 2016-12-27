package org.craftsmenlabs.stories.importer;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import mockit.Expectations;
import mockit.Mocked;

public class GithubAPIImporterTest
{
	private GithubAPIImporter githubAPIImporter = new GithubAPIImporter("http://foo.bar", "key", "authKey", "token");

	@Mocked
	private RestTemplate restTemplate;

	@Test
	public void testSuccessResponse() throws Exception
	{
		new Expectations()
		{{
			restTemplate.getForObject(withAny(""), withAny(String.class));
			result = readFile("github-test.json");

		}};

		Backlog backlog = githubAPIImporter.getBacklog();
		assertThat(backlog.getFeatures()).hasSize(2);
	}

	@Test(expected = StoriesException.class)
	public void testErrorResponse() throws Exception
	{
		new Expectations()
		{{
			restTemplate.getForObject(withAny(""), withAny(String.class));
			result = new StoriesException("");

		}};

		githubAPIImporter.getBacklog();
	}

	private String readFile(String resource) throws Exception
	{
		URL url = this.getClass().getClassLoader().getResource(resource);
		return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
	}
}
