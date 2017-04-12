package org.craftsmenlabs.stories.api.models.violation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class ViolationTest {

    private Violation violation;

    @Test
    public void setPoints() throws Exception {
        violation = new Violation();
        violation.setPoints(0.5, 1);

        assertThat(violation.getMissedPercentage()).isEqualTo(0.5);
        assertThat(violation.getScoredPercentage()).isEqualTo(0.5);
        assertThat(violation.getMissedPoints()).isEqualTo(0.5);
        assertThat(violation.getScoredPoints()).isEqualTo(0.5);
    }

    @Test
    public void testSetPoints() throws Exception {
        violation = new Violation();
        violation.setPoints(0.2, 0.3);

        assertThat(violation.getMissedPercentage()).isCloseTo(0.2, withinPercentage(0.1));
        assertThat(violation.getScoredPercentage()).isCloseTo(0.8, withinPercentage(0.1));
        assertThat(violation.getMissedPoints()).isCloseTo(0.06, withinPercentage(0.1));
        assertThat(violation.getScoredPoints()).isCloseTo(0.24, withinPercentage(0.1));
    }
}