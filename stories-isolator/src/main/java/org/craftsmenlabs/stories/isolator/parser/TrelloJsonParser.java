package org.craftsmenlabs.stories.isolator.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.TrelloJsonIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrelloJsonParser implements Parser
{

	private static final Logger _log = LoggerFactory.getLogger(TrelloJsonParser.class);

	public List<Issue> getIssues(String input)
	{
		List<TrelloJsonIssue> trelloJsonIssues = getTrelloJsonIssues(input);

		SentenceSplitter sentenceSplitter = new SentenceSplitter();

        List<Issue> result = new ArrayList<>();
        for (int i = 0; i < trelloJsonIssues.size(); i++) {
            TrelloJsonIssue trelloJsonIssue = trelloJsonIssues.get(i);

            String content = trelloJsonIssue.getDesc().length() == 0 ? trelloJsonIssue.getName() : trelloJsonIssue.getDesc();
            Issue issue = sentenceSplitter.splitSentence(content);
            issue.setKey(trelloJsonIssue.getId());
            issue.setRank(String.valueOf(i));
            issue.setEstimation(0f);

            result.add(issue);
        }

        return result;
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
