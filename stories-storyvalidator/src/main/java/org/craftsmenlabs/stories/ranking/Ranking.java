package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.util.List;

public interface Ranking {
    List<Double> getRanking(List<? extends BacklogItem> entries);
}
