package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;

import java.util.List;

/**
 *
 */
public class RankingExecutor
{
	private Ranking ranking;

	public RankingExecutor()
	{
//		Configuration configuration = new Configuration();
//		if (configuration.getDesiredRankingStrategy() == null || configuration.getDesiredRankingStrategy().length() == 0)
//		{
//			//ranking = new LinearRanking();
//		}
//		else
//		{
			ranking = chooseRankingStrategy("Curved");
//		}
	}

	public RankingExecutor(String strategy)
	{
		ranking = chooseRankingStrategy(strategy);
	}

	public void executeRanking(List<IssueValidatorEntry> list)
	{
		ranking.createRanking(BacklogValidatorEntry.builder().issueValidatorEntries(list).build());
	}

	private Ranking chooseRankingStrategy(String strategy)
	{
		switch (strategy)
		{
		case "curved":
			return new CurvedRanking();
//		case "linear":
//			return new LinearRanking();
//		case "binary":
//			return new BinaryRanking();
		default:
			return new CurvedRanking();
		}
	}
}
