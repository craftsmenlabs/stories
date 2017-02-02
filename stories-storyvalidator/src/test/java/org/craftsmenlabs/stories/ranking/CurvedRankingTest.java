package org.craftsmenlabs.stories.ranking;

import mockit.Injectable;
import mockit.Tested;
import org.assertj.core.util.FloatComparator;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    @Test
    public void testRankingReturnsPerfectCurvedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0002").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0003").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0004").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0005").build()).build()
        );
        curvedRanking.createRanking(issues);

        assertThat(issues).extracting(ValidatedBacklogItem::getScoredPercentage).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(1.0f, 0.96f, 0.84f, 0.64f, 0.359f);

        assertThat(issues).extracting(ValidatedBacklogItem::getMissedPercentage).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        assertThat(issues).extracting(ValidatedBacklogItem::getScoredPoints).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(0.26315f, 0.2526f, 0.2210f, 0.1684f, 0.094f);

        assertThat(issues).extracting(ValidatedBacklogItem::getMissedPoints).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        assertThat(issues.stream().mapToDouble(ValidatedBacklogItem::getScoredPoints).sum()).isCloseTo(1.0, withinPercentage(1.0));
        assertThat(issues.stream().mapToDouble(item -> item.getMissedPoints() + item.getScoredPoints()).sum()).isCloseTo(1.0, withinPercentage(0.01));

    }

    @Test
    public void testRankingReturnsInvertedPointsCurvedBacklog(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        int n = 5;
        List<ValidatedBacklogItem> issues = IntStream.iterate(0, i -> i + 1).limit(n)
                .mapToObj(i -> {
                    float x = (float) i / (n - 1);
                    return ValidatedFeature.builder().pointsValuation(x).feature(Feature.builder().rank("0|000" + i).build()).build();
                }).collect(Collectors.toList());
        curvedRanking.createRanking(issues);

        assertThat(issues).extracting(ValidatedBacklogItem::getScoredPercentage).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(0f, 0.24f, 0.42f, 0.48f, 0.36f);
        assertThat(issues).extracting(ValidatedBacklogItem::getMissedPercentage).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(1f, 0.72f, 0.42f, 0.16f, 0.0f);
        assertThat(issues).extracting(ValidatedBacklogItem::getScoredPoints).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(0.0f, 0.06315f, 0.1105f, 0.1263f, 0.094f);
        assertThat(issues).extracting(ValidatedBacklogItem::getMissedPoints).usingElementComparator(new FloatComparator(0.001f))
                .containsExactly(0.2631f, 0.18947f, 0.1105f, 0.0421f, 0.0f);

        assertThat(issues.stream().mapToDouble(item -> item.getMissedPoints() + item.getScoredPoints()).sum()).isCloseTo(1.0, withinPercentage(0.01));

    }

    @Test
    public void testRankingReturnsNormalizedPoints(@Injectable ValidatedBacklog validatedBacklog) throws Exception {
        final List<ValidatedBacklogItem> issues = Arrays.asList(
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0001").build()).build(),
                ValidatedFeature.builder().pointsValuation(1f).feature(Feature.builder().rank("0|0002").build()).build()
        );
        curvedRanking.createRanking(issues);

        assertThat(issues.get(0).getScoredPoints()).isCloseTo(0.5714f, withinPercentage(1.0));
        assertThat(issues.get(1).getScoredPoints()).isCloseTo(0.4285f, withinPercentage(1.0));

        assertThat(issues.get(0).getMissedPoints()).isCloseTo(0f, withinPercentage(1.0));
        assertThat(issues.get(1).getMissedPoints()).isCloseTo(0f, withinPercentage(1.0));
    }
}