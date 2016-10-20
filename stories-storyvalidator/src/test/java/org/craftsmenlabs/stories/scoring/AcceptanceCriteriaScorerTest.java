package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class AcceptanceCriteriaScorerTest {

    @Tested
    AcceptanceCriteriaScorer criteriaScorer;

    @Test
    public void testPerformScorer(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = "Given \n"
                    + "When \n"
                    + "Then .";


            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        float score = criteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(1.0f, withinPercentage(5));
    }

    @Test
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        List<Violation> v = new ArrayList<>();
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;

        }};

        float score = criteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorer_ReturnsNullOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = null;


            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        float score = criteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }
}
