package org.craftsmenlabs.stories.ranking;

import mockit.Tested;
import org.assertj.core.util.FloatComparator;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CurvedRankingTest {
    @Tested
    private CurvedRanking curvedRanking;


    @Test
    public void testGetRankingReturnsCurve(){
        curvedRanking = new CurvedRanking();
        final List<Float> ranking = curvedRanking.getRanking(Arrays.asList(new Feature(), new Feature(), new Feature(), new Feature()));

        assertThat(ranking).usingElementComparator(new FloatComparator(0.0001f)).containsExactly(1f, 0.9375f, 0.75f, 0.4375f);
    }

    @Test
    public void testGetRankingReturnsEmptyList(){
        curvedRanking = new CurvedRanking();
        final List<Float> ranking = curvedRanking.getRanking(Arrays.asList());

        assertThat(ranking.size()).isEqualTo(0);
    }
}