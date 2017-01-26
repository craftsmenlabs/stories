package org.craftsmenlabs.stories.isolator.parser;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.github.GithubJsonIssue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GithubJsonParser
{

    public Backlog parse(List<GithubJsonIssue> githubJsonIssues) {
        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        int rankLength = String.valueOf(githubJsonIssues.size()).length();

        Map<String, Feature> result = new HashMap<>();
        for (int i = 0; i < githubJsonIssues.size(); i++) {
            GithubJsonIssue githubJsonIssue = githubJsonIssues.get(i);

            String content = githubJsonIssue.getBody().length() == 0 ? githubJsonIssue.getTitle() : githubJsonIssue.getBody();
            Feature feature = sentenceSplitter.splitSentence(new Feature(), content);
            feature.setKey(Integer.toString(githubJsonIssue.getId()));

            String rankString = String.valueOf(i);

            String format2 = StringUtils.leftPad(rankString, rankLength, '0');
            feature.setRank(format2);
            feature.setEstimation(0f);

            result.put(feature.getKey(), feature);
        }

        return new Backlog(result);

    }
}
