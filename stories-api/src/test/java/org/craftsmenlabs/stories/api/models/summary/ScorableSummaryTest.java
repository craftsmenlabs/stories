package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.Rating;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScorableSummaryTest {
    @Test
    public void divideBy() throws Exception {
        ScorableSummary s = new ScorableSummary(30f, Rating.FAIL, 50);
        assertThat(s.divideBy(10)).isEqualTo(new ScorableSummary(3f, null, 5));
    }

    @Test
    public void add() throws Exception {
        ScorableSummary a = new ScorableSummary(30f, Rating.FAIL, 50);
        ScorableSummary b = new ScorableSummary(3f, Rating.FAIL, 5);
        ScorableSummary c = new ScorableSummary(33f, null, 55);
        assertThat(a.plus(b)).isEqualTo(c);
    }

}