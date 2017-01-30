package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.items.types.Rankable;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CurvedRanking implements Ranking {
    public static final int SMOOTH_CURVE = 2;

    @Override
    public float createRanking(List<ValidatedBacklogItem> entries) {
        if (entries == null || entries.size() == 0) {
            return 0.0f;
        }

        entries = entries.stream()
                .sorted(Comparator.comparing(Rankable::getRank))
                .collect(Collectors.toList());



        float scoredPoints = 0f;
        final float entriesSize = entries.size();
        float couldHaveScored = (float) IntStream.range(0, entries.size()).mapToDouble(i -> curvedQuotient(i, entriesSize)).sum();

        for (int i = 0; i < entries.size(); i++) {
            float curvedQuotient = curvedQuotient(i, entries.size());
            ValidatedBacklogItem entry = entries.get(i);
            scoredPoints += entry.getPointsValuation() * curvedQuotient;
            entry.setBacklogPoints(entry.getPointsValuation() * curvedQuotient);
            entry.setPotentialBacklogPoints(curvedQuotient);
            entry.setNormalizedBacklogPoints((entry.getPointsValuation() * curvedQuotient) / couldHaveScored);
        }
        return  scoredPoints / couldHaveScored;
    }

    public float curvedQuotient(float position, float amountOfItems) {
        float part = position / amountOfItems;
        return 1 - (float) (Math.pow(part, SMOOTH_CURVE));
    }
}
