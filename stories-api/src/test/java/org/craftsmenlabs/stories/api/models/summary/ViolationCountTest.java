package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ViolationCountTest {


    @Test
    public void testAddReturnsTwo() throws Exception {
        ViolationCount a = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        ViolationCount b = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        ViolationCount expected = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 2);
        }};
        assertThat(a.add(b)).isEqualTo(expected);
    }

    @Test
    public void testAddReturnsOneOneOnDifferentKeys() throws Exception {
        ViolationCount a = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        ViolationCount b = new ViolationCount(){{
            put(ViolationType.BacklogRatingViolation, 1);
        }};
        ViolationCount expected = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
            put(ViolationType.BacklogRatingViolation, 1);
        }};
        assertThat(a.add(b)).isEqualTo(expected);
    }

    @Test
    public void testAddReturnsOneOnOneAndEmpty() throws Exception {
        ViolationCount a = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        ViolationCount b = new ViolationCount(){{}};

        ViolationCount expected = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        
        assertThat(a.add(b)).isEqualTo(expected);
    }

    @Test
    public void testMinusReturnsTwo() throws Exception {
        ViolationCount a = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 2);
        }};
        ViolationCount b = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        ViolationCount expected = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        assertThat(a.minus(b)).isEqualTo(expected);
    }

    @Test
    public void testMinusReturnsOneOneOnDifferentKeys() throws Exception {
        ViolationCount a = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        ViolationCount b = new ViolationCount(){{
            put(ViolationType.BacklogRatingViolation, 1);
        }};
        ViolationCount expected = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
            put(ViolationType.BacklogRatingViolation, -1);
        }};
        assertThat(a.minus(b)).isEqualTo(expected);
    }

    @Test
    public void testMinusReturnsOneOnOneAndEmpty() throws Exception {
        ViolationCount a = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        ViolationCount b = new ViolationCount(){{}};

        ViolationCount expected = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};
        
        assertThat(a.minus(b)).isEqualTo(expected);
    }


    @Test
    public void testDivideByReturnsZeroOnDivByZero(){
        ViolationCount a = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 1);
        }};

        ViolationCount expected = new ViolationCount(){{
            put(ViolationType.BacklogEmptyViolation, 0);
        }};


        assertThat(a.divideBy(0).get(ViolationType.BacklogEmptyViolation)).isEqualTo(0);
    }

}