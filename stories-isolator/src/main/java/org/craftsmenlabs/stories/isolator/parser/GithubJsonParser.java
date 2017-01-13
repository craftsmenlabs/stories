package org.craftsmenlabs.stories.isolator.parser;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.items.Backlog;
import org.craftsmenlabs.stories.api.models.items.Feature;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.github.GithubJsonIssue;

import java.util.ArrayList;
import java.util.List;

public class GithubJsonParser
{

    public Backlog parse(List<GithubJsonIssue> githubJsonIssues) {
        Backlog backlog = new Backlog();
        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        int rankLength = String.valueOf(githubJsonIssues.size()).length();

        List<Feature> result = new ArrayList<>();
        for (int i = 0; i < githubJsonIssues.size(); i++) {
            GithubJsonIssue githubJsonIssue = githubJsonIssues.get(i);

            String content = githubJsonIssue.getBody().length() == 0 ? githubJsonIssue.getTitle() : githubJsonIssue.getBody();
            Feature feature = sentenceSplitter.splitSentence(new Feature(), content);
            feature.setKey(Integer.toString(githubJsonIssue.getId()));

            String rankString = String.valueOf(i);

            String format2 = StringUtils.leftPad(rankString, rankLength, '0');
            feature.setRank(format2);
            feature.setEstimation(0f);

            result.add(feature);
        }
        backlog.setFeatures(result);

        return backlog;

    }
}
