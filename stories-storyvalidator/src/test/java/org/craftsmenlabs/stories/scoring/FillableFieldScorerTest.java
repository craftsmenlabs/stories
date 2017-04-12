package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBug;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings({"ResultOfMethodCallIgnored", "Duplicates"})
public class FillableFieldScorerTest {

    @Test
    public void testValidateEmptyPriorityScoresZero(@Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations(){{
            validationConfig.getBug().getEnabledFields();
            result = Arrays.asList("priority");

            validationConfig.getBug().getRatingThreshold();
            result = 50;
        }};

        final ValidatedBug validatedBug = (ValidatedBug) new FillableFieldScorer(1.0, validationConfig).validate(Bug.empty());
        double score = validatedBug.getScoredPoints();

        assertThat(score).isCloseTo(0.0, withinPercentage(1));
        assertThat(validatedBug.getAllViolations()).hasSize(1);
        assertThat(validatedBug.getAllViolations().get(0))
                .isEqualTo(new Violation(ViolationType.FieldEmptyViolation, "Field priority was found to be empty while it should be filled.", 1.0));
    }

    @Test
    public void testValidateScoresFullPoints(@Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations(){{
            validationConfig.getBug().getEnabledFields();
            result = Arrays.asList("priority", "reproduction_path", "environment", "expected_behaviour", "acceptation_criteria");

            validationConfig.getBug().getRatingThreshold();
            result = 50;
        }};

        final Bug bug = new Bug("summary", "description", "repro", "env", "expBehav", "accept", "prio", "1", "1", "1", null, null);
        final ValidatedBug validatedBug = (ValidatedBug) new FillableFieldScorer(1.0, validationConfig).validate(bug);
        double score = validatedBug.getScoredPoints();

        assertThat(score).isCloseTo(1.0, withinPercentage(1));
        assertThat(validatedBug.getAllViolations()).hasSize(0);
        assertThat(validatedBug.getRating()).isEqualTo(Rating.SUCCESS);
    }

    @Test
    public void testValidateScoresHalfPoints(@Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations(){{
            validationConfig.getBug().getEnabledFields();
            result = Arrays.asList("priority", "reproduction_path", "environment", "expected_behaviour", "acceptation_criteria");

            validationConfig.getBug().getRatingThreshold();
            result = 50;
        }};

        final Bug bug = new Bug("summary", "description", "repro", "env", "expBehav", null, null, "1", "1", "1", null, null);
        final ValidatedBug validatedBug = (ValidatedBug) new FillableFieldScorer(0.2, validationConfig).validate(bug);
        double score = validatedBug.getScoredPoints();

        assertThat(score).isCloseTo(0.2 * 0.6, withinPercentage(1));
        assertThat(validatedBug.getAllViolations()).hasSize(2);
        assertThat(validatedBug.getRating()).isEqualTo(Rating.SUCCESS);
    }

    @Test
    public void testValidateWrongField(@Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations(){{
            validationConfig.getBug().getEnabledFields();
            result = Arrays.asList("nofield");
        }};

        assertThatExceptionOfType(StoriesException.class).isThrownBy(
                () -> new FillableFieldScorer(1.0, validationConfig).validate(Bug.empty()));
    }

}