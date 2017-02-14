package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

@SuppressWarnings({"ResultOfMethodCallIgnored", "Duplicates"})
public class EstimationScorerTest {
    @Test
    public void validateReturnsZeroOnNull(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            validationConfig.getEstimation().getRatingThreshold();
            result = 0.7f;
        }};

        final ValidatedEstimation validatedEstimation = new EstimationScorer(1f, validationConfig).validate(null);
        float score = validatedEstimation.getScoredPoints();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
        assertThat(validatedEstimation.getViolations()).hasSize(1);
    }

    @Test
    public void testPerformScorerReturnsOneOnZero(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getEstimation();
            result = 0f;


            validationConfig.getEstimation().getRatingThreshold();
            result = 0.1f;
        }};
        ValidatedEstimation entry1 = new EstimationScorer(1f, validationConfig).validate(entry.getItem().getEstimation());
        assertThat(entry1.getScoredPoints()).isCloseTo(0f, withinPercentage(1));
        assertThat(entry1.getRating()).isEqualTo(Rating.FAIL);
        assertThat(entry1.getViolations()).hasSize(1);
    }

    @Test
    public void testvalidateReturnsOneOnValidEstimation(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getEstimation();
            result = 1f;


            validationConfig.getEstimation().getRatingThreshold();
            result = 1f;
        }};

        ValidatedEstimation entry1 = new EstimationScorer(1f, validationConfig).validate(entry.getItem().getEstimation());
        assertThat(entry1.getScoredPoints()).isCloseTo(1f, withinPercentage(1));
        assertThat(entry1.getViolations()).hasSize(0);
    }

    @Test
    public void testvalidateReturnsSuccesOnHighScore(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getEstimation();
            result = 1f;

            validationConfig.getEstimation().getRatingThreshold();
            result = 1f;
        }};

        ValidatedEstimation entry1 = new EstimationScorer(1f, validationConfig).validate(entry.getItem().getEstimation());
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
        assertThat(entry1.getViolations()).hasSize(0);
    }

}
