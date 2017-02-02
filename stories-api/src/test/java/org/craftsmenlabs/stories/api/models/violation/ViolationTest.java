package org.craftsmenlabs.stories.api.models.violation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ViolationTest {

    private Violation violation;

    @Test
    public void setPoints() throws Exception {
        violation = new Violation();
        violation.setPoints(0.5f, 1);

        assertThat(violation.getMissedPercentage()).isEqualTo(0.5f);
        assertThat(violation.getScoredPercentage()).isEqualTo(0.5f);
        assertThat(violation.getMissedPoints()).isEqualTo(0.5f);
        assertThat(violation.getMissedPoints()).isEqualTo(0.5f);
    }

}