package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.FeatureValidatorEntry;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class EstimationScorerTest {
    @Test
    public void performScorerReturnsZeroOnNull(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getFeature().getEstimation();
            result = null;

            validationConfig.getEstimation().getRatingtreshold();
            result = 0.7f;
        }};

        float score = EstimationScorer.performScorer(entry.getFeature().getEstimation(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsZeroOnZero(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getFeature().getEstimation();
            result = 0f;


            validationConfig.getEstimation().getRatingtreshold();
            result = 0.1f;
        }};

        EstimationValidatorEntry entry1 = EstimationScorer.performScorer(entry.getFeature().getEstimation(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0f, withinPercentage(1));
        assertThat(entry1.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerReturnsOneOnValidEstimation(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getFeature().getEstimation();
            result = 1f;


            validationConfig.getEstimation().getRatingtreshold();
            result = 1f;
        }};

        EstimationValidatorEntry entry1 = EstimationScorer.performScorer(entry.getFeature().getEstimation(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsSuccesOnHighScore(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getFeature().getEstimation();
            result = 1f;

            validationConfig.getEstimation().getRatingtreshold();
            result = 1f;
        }};

        EstimationValidatorEntry entry1 = EstimationScorer.performScorer(entry.getFeature().getEstimation(), validationConfig);
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
    }

}
