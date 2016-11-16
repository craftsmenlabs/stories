package org.craftsmenlabs.stories.ranking;

import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.junit.Test;

/**
 * Interface to implement all ranking algo's with.
 */
public interface RankingTest
{
	@Test void testRankingReturnsZeroOnNull() throws Exception;

	@Test void testRankingReturnsZeroOnNullBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception;

	@Test void testRankingReturnsZeroOnEmptyBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception;

	@Test void testRankingIsZeroWithOnlyUnscoredItems(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception;

	@Test void testRankingReturnsZeroOnZeroScoreBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception;

	@Test void testRankingReturnsOneOnPerfectBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception;

	@Test void testRankingReturnsScoreOnGoodGradientMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception;

	@Test void testRankingReturnsScoreOnBadGradientMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception;
}
