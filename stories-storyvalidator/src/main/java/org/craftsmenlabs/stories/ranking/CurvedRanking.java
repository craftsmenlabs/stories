package org.craftsmenlabs.stories.ranking;

import java.util.List;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;

public class CurvedRanking implements Ranking
{

	public static final int SMOOTH_CURVE = 2;

	public float createRanking(BacklogValidatorEntry backlogValidatorEntry)
	{
		if (backlogValidatorEntry == null ||
            backlogValidatorEntry.getIssueValidatorEntries() == null ||
            backlogValidatorEntry.getIssueValidatorEntries().size() == 0 )
		{
			return 0.0f;
		}
		List<IssueValidatorEntry> entries = backlogValidatorEntry.getIssueValidatorEntries();
		float scoredPoints = 0f;
		float couldHaveScored = 0f;
		for (int i = 0; i < entries.size(); i++)
		{
			float curvedQuotient = curvedQuotient(i, entries.size());
			couldHaveScored += curvedQuotient;
			scoredPoints += entries.get(i).getPointsValuation() * curvedQuotient;
		}
		return scoredPoints / couldHaveScored;
	}

	public float curvedQuotient(float position, float amountOfItems)
	{
		float part = position / amountOfItems;
		return 1 - (float)(Math.pow(part, SMOOTH_CURVE));
	}
}
