package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.validatorentry.BacklogItem;
import org.craftsmenlabs.stories.api.models.validatorentry.Rankable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CurvedRanking implements Ranking
{
	public static final int SMOOTH_CURVE = 2;

    @Override
    public float createRanking(List entries) {
        return createRankingConcrete(entries);
    }

    public float createRankingConcrete(List<? extends BacklogItem> entries) {
        if (entries == null || entries.size() == 0) {
			return 0.0f;
		}

        List<? extends BacklogItem> entries2 = entries.stream()
                .sorted(Comparator.comparing(Rankable::getRank))
                .collect(Collectors.toList());

		float scoredPoints = 0f;
		float couldHaveScored = 0f;
		for (int i = 0; i < entries2.size(); i++)
		{
			float curvedQuotient = curvedQuotient(i, entries2.size());
			couldHaveScored += curvedQuotient;
			scoredPoints += entries2.get(i).getPointsValuation() * curvedQuotient;
		}
		return scoredPoints / couldHaveScored;
	}

	public float curvedQuotient(float position, float amountOfItems)
	{
		float part = position / amountOfItems;
		return 1 - (float)(Math.pow(part, SMOOTH_CURVE));
	}

}
