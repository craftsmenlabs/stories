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
            ValidatedBacklogItem entry = entries.get(i);

            float curvedQuotient = curvedQuotient(i, entries.size());
            final float scoredPercentage = entry.getPointsValuation() * curvedQuotient;
            final float missedPercentage = curvedQuotient - (entry.getPointsValuation() * curvedQuotient);
            scoredPoints += scoredPercentage;

            entry.setScoredPercentage(scoredPercentage);
            entry.setMissedPercentage(missedPercentage);
            entry.setScoredPoints(scoredPercentage / couldHaveScored);
            entry.setMissedPoints(missedPercentage / couldHaveScored);
        }
        return  scoredPoints / couldHaveScored;
    }

    public float curvedQuotient(float position, float amountOfItems) {
        float part = position / amountOfItems;
        return 1 - (float) (Math.pow(part, SMOOTH_CURVE));
    }
}
