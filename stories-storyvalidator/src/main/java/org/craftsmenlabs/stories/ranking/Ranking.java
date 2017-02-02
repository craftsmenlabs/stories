package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;

import java.util.List;

public interface Ranking {
    float createRanking(List<ValidatedBacklogItem> entries);

    List<Float> getRanking();

    List<Float> getRanking(int size);
}
