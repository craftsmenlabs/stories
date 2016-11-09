package org.craftsmenlabs.stories.isolator;

import mockit.Tested;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.isolator.testutil.RetrieveTestData;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrelloJsonParserTest
{
	@Tested
	private TrelloJsonParser _trelloJsonParser;

	@Test
	public void getIssuesTest()
	{
		List<Issue> issues = _trelloJsonParser.getIssues(RetrieveTestData.getExportedTrelloJSONTestResultFromResource());

		assertThat(issues).extracting(issue -> issue.getKey()).containsOnly("581b199ba7dfd7e8f737262c");
	}

	@Test
	public void getTrelloJsonIssuesTest()
	{
		List<TrelloJsonIssue> issues =
			_trelloJsonParser.getTrelloJsonIssues(RetrieveTestData.getExportedTrelloJSONTestResultFromResource());

		assertThat(issues).extracting(issue -> issue.getId()).containsOnly("581b199ba7dfd7e8f737262c");
	}
}
