package org.craftsmenlabs.stories.ranking;

import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.junit.Test;

/**
 * Interface to implement all ranking algo's with.
 */
public interface RankingTest
{
	@Test void testRankingReturnsZeroOnNull() throws Exception;

    @Test
    void testRankingReturnsZeroOnNullBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception;

    @Test
    void testRankingReturnsZeroOnEmptyBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception;

    @Test
    void testRankingIsZeroWithOnlyUnscoredItems(@Injectable ValidatedBacklog validatedBacklog) throws Exception;

    @Test
    void testRankingReturnsZeroOnZeroScoreBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception;

    @Test
    void testRankingReturnsOneOnPerfectBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception;

    @Test
    void testRankingReturnsScoreOnGoodGradientMixedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception;

    @Test
    void testRankingReturnsScoreOnBadGradientMixedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception;
}
