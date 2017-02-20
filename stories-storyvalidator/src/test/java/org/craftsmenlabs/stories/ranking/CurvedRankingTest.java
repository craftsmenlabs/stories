package org.craftsmenlabs.stories.ranking;

import mockit.Tested;
import org.assertj.core.util.DoubleComparator;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CurvedRankingTest {
    @Tested
    private CurvedRanking curvedRanking;


    @Test
    public void testGetRankingReturnsCurve() {
        curvedRanking = new CurvedRanking();
        final List<Double> ranking = curvedRanking.getRanking(Arrays.asList(new Feature(), new Feature(), new Feature(), new Feature()));

        assertThat(ranking).usingElementComparator(new DoubleComparator(0.0001)).containsExactly(32.0, 30.0, 24.0, 14.0);
    }

    @Test
    public void testGetRankingReturnsEmptyList() {
        curvedRanking = new CurvedRanking();
        final List<Double> ranking = curvedRanking.getRanking(Arrays.asList());

        assertThat(ranking.size()).isEqualTo(0);
    }
}