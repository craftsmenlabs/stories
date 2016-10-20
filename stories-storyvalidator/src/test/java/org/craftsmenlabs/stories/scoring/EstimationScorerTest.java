package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class EstimationScorerTest {

    @Tested
    EstimationScorer estimationScorer;

    @Test
    public void performScorer_ReturnsZeroOnNull(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = null;

            validationConfig.getEstimation().getRatingtreshold();
            result = 0.7f;
        }};

        float score = estimationScorer.performScorer(entry.getIssue().getEstimation(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorer_ReturnsZeroOnZero(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = 0f;


            validationConfig.getEstimation().getRatingtreshold();
            result = 0.7f;
        }};

        float score = estimationScorer.performScorer(entry.getIssue().getEstimation(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorer_ReturnsOneOnValidEstimation(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getEstimation();
            result = 3f;


            validationConfig.getEstimation().getRatingtreshold();
            result = 0.7f;
        }};

        float score = estimationScorer.performScorer(entry.getIssue().getEstimation(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(1f, withinPercentage(1));
    }

}
