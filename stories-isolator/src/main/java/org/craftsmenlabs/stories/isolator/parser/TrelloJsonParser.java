package org.craftsmenlabs.stories.isolator.parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrelloJsonParser implements Parser
{

	private static final Logger _log = LoggerFactory.getLogger(TrelloJsonParser.class);

	public List<Issue> getIssues(String input)
	{

		List<TrelloJsonIssue> trelloJsonIssues = getTrelloJsonIssues(input);

		SentenceSplitter sentenceSplitter = new SentenceSplitter();

		return trelloJsonIssues.stream()
			.map(trelloJsonIssue ->
			{
				Issue issue = sentenceSplitter.splitSentence(trelloJsonIssue.getDesc());
				issue.setKey(trelloJsonIssue.getId());
				issue.setRank("Major");
				issue.setEstimation(0f);

				return issue;
			}).collect(Collectors.toList());
	}

	public List<TrelloJsonIssue> getTrelloJsonIssues(String input)
	{
		List<TrelloJsonIssue> trelloJsonIssues = null;

		try
		{
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<TrelloJsonIssue>> mapType = new TypeReference<List<TrelloJsonIssue>>() {};
			trelloJsonIssues = mapper.readValue(input, mapType);
		}
		catch (JsonParseException | JsonMappingException e)
		{
			_log.info("Error getting Trello JSON issues", e);
		}
		catch (IOException e)
		{
			_log.info("Error getting Trello JSON issues", e);
		}

		return trelloJsonIssues;
	}
}
