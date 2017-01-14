package org.craftsmenlabs.stories.isolator.parser;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;

import java.util.ArrayList;
import java.util.List;

public class TrelloJsonParser {

    public Backlog parse(List<TrelloJsonIssue> trelloJsonIssues) {
        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        int rankLength = String.valueOf(trelloJsonIssues.size()).length();

        List<Feature> result = new ArrayList<>();
        for (int i = 0; i < trelloJsonIssues.size(); i++) {
            TrelloJsonIssue trelloJsonIssue = trelloJsonIssues.get(i);

            String content = trelloJsonIssue.getDesc().length() == 0 ? trelloJsonIssue.getName() : trelloJsonIssue.getDesc();
            Feature feature = sentenceSplitter.splitSentence(new Feature(), content);
            feature.setKey(trelloJsonIssue.getId());
            feature.setExternalURI(trelloJsonIssue.getUrl());

            String rankString = String.valueOf(i);

            String format2 = StringUtils.leftPad(rankString, rankLength, '0');
            feature.setRank(format2);
            feature.setEstimation(0f);

            result.add(feature);
        }

        return new Backlog(result);

    }
}
