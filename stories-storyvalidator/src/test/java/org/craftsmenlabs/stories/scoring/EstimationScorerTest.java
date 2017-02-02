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

@SuppressWarnings("ResultOfMethodCallIgnored")
public class EstimationScorerTest {
    @Test
    public void performScorerReturnsZeroOnNull(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getEstimation();
            result = null;

            validationConfig.getEstimation().getRatingThreshold();
            result = 0.7f;
        }};

        float score = EstimationScorer.performScorer(entry.getItem().getEstimation(), 1f, validationConfig).getScoredPoints();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsZeroOnZero(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getEstimation();
            result = 0f;


            validationConfig.getEstimation().getRatingThreshold();
            result = 0.1f;
        }};

        ValidatedEstimation entry1 = EstimationScorer.performScorer(entry.getItem().getEstimation(), 1f, validationConfig);
        assertThat(entry1.getScoredPoints()).isCloseTo(0f, withinPercentage(1));
        assertThat(entry1.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerReturnsOneOnValidEstimation(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getEstimation();
            result = 1f;


            validationConfig.getEstimation().getRatingThreshold();
            result = 1f;
        }};

        ValidatedEstimation entry1 = EstimationScorer.performScorer(entry.getItem().getEstimation(), 1f, validationConfig);
        assertThat(entry1.getScoredPoints()).isCloseTo(1f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsSuccesOnHighScore(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getEstimation();
            result = 1f;

            validationConfig.getEstimation().getRatingThreshold();
            result = 1f;
        }};

        ValidatedEstimation entry1 = EstimationScorer.performScorer(entry.getItem().getEstimation(), 1f, validationConfig);
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
    }

}
