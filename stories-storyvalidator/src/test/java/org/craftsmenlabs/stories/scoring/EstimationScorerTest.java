package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class EstimationScorerTest {
    @Test
    public void performScorerReturnsZeroOnNull(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = null;

            validationConfig.getEstimation().getRatingtreshold();
            result = 0.7f;
        }};

        float score = EstimationScorer.performScorer(entry.getIssue().getEstimation(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsZeroOnZero(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = 0f;


            validationConfig.getEstimation().getRatingtreshold();
            result = 0.1f;
        }};

        EstimationValidatorEntry entry1 = EstimationScorer.performScorer(entry.getIssue().getEstimation(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0f, withinPercentage(1));
        assertThat(entry1.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerReturnsOneOnValidEstimation(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = 1f;


            validationConfig.getEstimation().getRatingtreshold();
            result = 1f;
        }};

        EstimationValidatorEntry entry1 = EstimationScorer.performScorer(entry.getIssue().getEstimation(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsSuccesOnHighScore(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = 1f;

            validationConfig.getEstimation().getRatingtreshold();
            result = 1f;
        }};

        EstimationValidatorEntry entry1 = EstimationScorer.performScorer(entry.getIssue().getEstimation(), validationConfig);
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCES);
    }

}
