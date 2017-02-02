package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CurvedRanking implements Ranking {
    public static final int SMOOTH_CURVE = 2;
    private List<? extends BacklogItem> entries;


    @Override
    public List<Float> getRanking(List<? extends BacklogItem> entries) {
        this.entries = entries;
        return IntStream.range(0, entries.size())
            .mapToObj(this::curvedQuotient)
            .collect(Collectors.toList());
    }

    private float curvedQuotient(int position) {
        return curvedQuotient(position, entries.size());
    }

    private float curvedQuotient(int position, int size) {
        float part = ((float)position) / size;
        return 1f - (float)(Math.pow(part, SMOOTH_CURVE));
    }
}
