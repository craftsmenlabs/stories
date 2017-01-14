package org.craftsmenlabs.stories.ranking;

import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class CurvedRankingTest implements RankingTest {
    @Tested
    private CurvedRanking curvedRanking;

    @Override
    @Test
    public void testRankingReturnsZeroOnNull() throws Exception {
        assertThat(curvedRanking.createRanking(null)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsZeroOnNullBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        assertThat(curvedRanking.createRanking(null)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsZeroOnEmptyBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {

        assertThat(curvedRanking.createRanking(new ArrayList<>())).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingIsZeroWithOnlyUnscoredItems(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().feature(Feature.builder().rank("0|0002").build()).build(),
                ValidatedFeature.builder().feature(Feature.builder().rank("0|0003").build()).build(),
                ValidatedFeature.builder().feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().feature(Feature.builder().rank("0|0005").build()).build()
        );

        assertThat(curvedRanking.createRanking(issues)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsZeroOnZeroScoreBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0002").build()).build(),
                ValidatedFeature.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0003").build()).build(),
                ValidatedFeature.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0005").build()).build()
        );

        assertThat(curvedRanking.createRanking(issues)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsOneOnPerfectBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0002").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0003").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0005").build()).build()
        );

        assertThat(curvedRanking.createRanking(issues)).isCloseTo(1f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsScoreOnGoodGradientMixedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0000").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.8f).feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.6f).feature(Feature.builder().rank("0|0002").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.4f).feature(Feature.builder().rank("0|0003").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.2f).feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0005").build()).build()
        );

        assertThat(curvedRanking.createRanking(issues)).isCloseTo(0.608f, withinPercentage(1.0));
    }


    @Override
    @Test
    public void testRankingReturnsScoreOnBadGradientMixedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.2f).feature(Feature.builder().rank("0|0002").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.4f).feature(Feature.builder().rank("0|0003").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.6f).feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.8f).feature(Feature.builder().rank("0|0005").build()).build(),
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0006").build()).build()
        );

        assertThat(curvedRanking.createRanking(issues)).isCloseTo(0.391f, withinPercentage(1.0));
    }

    @Test
    public void testRankingReturnsHighScoreOnGoodMixedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0002").build()).build(),
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0003").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0005").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0006").build()).build()
        );

        assertThat(curvedRanking.createRanking(issues)).isGreaterThanOrEqualTo(0.5f);
    }

    @Test
    public void testRankingReturnsLowScoreOnBadMixedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0007").build()).build(),
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0008").build()).build(),
                ValidatedFeature.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0009").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0005").build()).build(),
                ValidatedFeature.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0006").build()).build()
        );

        assertThat(curvedRanking.createRanking(issues)).isLessThanOrEqualTo(0.5f);
    }

}
