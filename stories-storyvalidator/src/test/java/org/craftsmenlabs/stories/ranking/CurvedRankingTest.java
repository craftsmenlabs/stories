package org.craftsmenlabs.stories.ranking;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.FeatureValidatorEntry;
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
    public void testRankingReturnsZeroOnNullBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = null;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsZeroOnEmptyBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = new ArrayList<>();
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingIsZeroWithOnlyUnscoredItems(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        List<FeatureValidatorEntry> issues = Arrays.asList(
                FeatureValidatorEntry.builder().feature(Feature.builder().rank("0|0001").build()).build(),
                FeatureValidatorEntry.builder().feature(Feature.builder().rank("0|0002").build()).build(),
                FeatureValidatorEntry.builder().feature(Feature.builder().rank("0|0003").build()).build(),
                FeatureValidatorEntry.builder().feature(Feature.builder().rank("0|0004").build()).build(),
                FeatureValidatorEntry.builder().feature(Feature.builder().rank("0|0005").build()).build()
        );

        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsZeroOnZeroScoreBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        List<FeatureValidatorEntry> issues = Arrays.asList(
                FeatureValidatorEntry.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0001").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0002").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0003").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0004").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0f).feature(Feature.builder().rank("0|0005").build()).build()
        );

        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsOneOnPerfectBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        List<FeatureValidatorEntry> issues = Arrays.asList(
                FeatureValidatorEntry.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0001").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0002").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0003").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0004").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0005").build()).build()
        );

        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(1f, withinPercentage(1.0));
    }

    @Override
    @Test
    public void testRankingReturnsScoreOnGoodGradientMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        List<FeatureValidatorEntry> issues = Arrays.asList(
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0000").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.8f).feature(Feature.builder().rank("0|0001").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.6f).feature(Feature.builder().rank("0|0002").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.4f).feature(Feature.builder().rank("0|0003").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.2f).feature(Feature.builder().rank("0|0004").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0005").build()).build()
        );

        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0.608f, withinPercentage(1.0));
    }


    @Override
    @Test
    public void testRankingReturnsScoreOnBadGradientMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        List<FeatureValidatorEntry> issues = Arrays.asList(
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0001").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.2f).feature(Feature.builder().rank("0|0002").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.4f).feature(Feature.builder().rank("0|0003").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.6f).feature(Feature.builder().rank("0|0004").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.8f).feature(Feature.builder().rank("0|0005").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0006").build()).build()
        );

        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0.391f, withinPercentage(1.0));
    }

    @Test
    public void testRankingReturnsHighScoreOnGoodMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        List<FeatureValidatorEntry> issues = Arrays.asList(
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0001").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0002").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0003").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0004").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0005").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0006").build()).build()
        );

        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isGreaterThanOrEqualTo(0.5f);
    }

    @Test
    public void testRankingReturnsLowScoreOnBadMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry) throws Exception {
        List<FeatureValidatorEntry> issues = Arrays.asList(
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0007").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0008").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(1.0f).feature(Feature.builder().rank("0|0009").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0004").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0005").build()).build(),
                FeatureValidatorEntry.builder().pointsValuation(0.0f).feature(Feature.builder().rank("0|0006").build()).build()
        );

        new Expectations() {{
            backlogValidatorEntry.getFeatureValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isLessThanOrEqualTo(0.5f);
    }

}
