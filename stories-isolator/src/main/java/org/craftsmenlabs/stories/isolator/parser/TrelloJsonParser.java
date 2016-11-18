package org.craftsmenlabs.stories.isolator.parser;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;

import java.util.ArrayList;
import java.util.List;

public class TrelloJsonParser {

    public List<Issue> parse(List<TrelloJsonIssue> trelloJsonIssues) {
        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        int rankLength = String.valueOf(trelloJsonIssues.size()).length();

        List<Issue> result = new ArrayList<>();
        for (int i = 0; i < trelloJsonIssues.size(); i++) {
            TrelloJsonIssue trelloJsonIssue = trelloJsonIssues.get(i);


            String content = trelloJsonIssue.getDesc().length() == 0 ? trelloJsonIssue.getName() : trelloJsonIssue.getDesc();
            Issue issue = sentenceSplitter.splitSentence(content);
            issue.setKey(trelloJsonIssue.getId());

            String rankString = String.valueOf(i);

            String format2 = StringUtils.leftPad(rankString, rankLength, '0');
            issue.setRank(format2);
            issue.setEstimation(0f);

            result.add(issue);
        }

        return result;

    }
}
