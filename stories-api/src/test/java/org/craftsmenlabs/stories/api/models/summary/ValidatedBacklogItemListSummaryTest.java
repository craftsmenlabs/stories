package org.craftsmenlabs.stories.api.models.summary;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatedBacklogItemListSummaryTest {

    @Test
    public void testdivideByReturnsCorrectResult() throws Exception {
        BacklogItemListSummary b = new BacklogItemListSummary(30, 40, 50);
        assertThat(b.divideBy(10)).isEqualTo(new BacklogItemListSummary(3, 4, 5));
    }

    @Test
    public void add() throws Exception {
        BacklogItemListSummary a = new BacklogItemListSummary(30, 40, 50);
        BacklogItemListSummary b = new BacklogItemListSummary(1, 2, 3);
        BacklogItemListSummary c = new BacklogItemListSummary(31, 42, 53);

        assertThat(a.plus(b)).isEqualTo(c);
    }
}