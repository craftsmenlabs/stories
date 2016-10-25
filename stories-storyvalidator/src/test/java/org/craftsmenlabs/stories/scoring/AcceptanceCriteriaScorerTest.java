package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.CriteriaViolation;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.ViolationType;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class AcceptanceCriteriaScorerTest {

    @Tested
    private AcceptanceCriteriaScorer criteriaScorer;

    @Test
    public void testPerformScorer(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getAcceptanceCriteria();
            result = "Given Given Given \n"
                    + "When When When \n"
                    + "Then Then Then .";


            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        float score = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
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

        float score = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
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

        float score = AcceptanceCriteriaScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorer_ReturnsLowScoreWhenLengthIsTooShort(@Injectable IssueValidatorEntry entryTooShort, @Injectable IssueValidatorEntry entryLong, @Injectable ScorerConfigCopy validationConfig){
        String testCriteria = Stream
                .generate(() -> new String("givenwhenthen"))
                .limit(100)
                .collect(Collectors.joining());

        new Expectations(){{
            entryTooShort.getIssue().getAcceptanceCriteria();
            result = testCriteria.substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA - 1);


            entryLong.getIssue().getAcceptanceCriteria();
            result = testCriteria.substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA);

            validationConfig.getCriteria().getRatingtreshold();
            result = 0.7f;
        }};

        float scoreTooShort = AcceptanceCriteriaScorer.performScorer(entryTooShort.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        float scoreLong = AcceptanceCriteriaScorer.performScorer(entryLong.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(scoreTooShort).isLessThan(scoreLong);
    }

    @Test
    public void testPerformScorer_AddsGivenClauseViolationOnNoGiven(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig){
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry
                = AcceptanceCriteriaScorer.performScorer("when then when then when then when then", validationConfig);
        assertThat(acceptanceCriteriaValidatorEntry.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(acceptanceCriteriaValidatorEntry.getViolations()).contains(new CriteriaViolation(ViolationType.CriteriaGivenClauseViolation, ""));
    }

    @Test
    public void testPerformScorer_AddsWhenClauseViolationOnNoGiven(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig){
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry
                = AcceptanceCriteriaScorer.performScorer("given then given then given then given then", validationConfig);
        assertThat(acceptanceCriteriaValidatorEntry.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(acceptanceCriteriaValidatorEntry.getViolations()).contains(new CriteriaViolation(ViolationType.CriteriaWhenClauseViolation, ""));
    }
    
    @Test
    public void testPerformScorer_AddsThenClauseViolationOnNoGiven(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig){
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry
                = AcceptanceCriteriaScorer.performScorer("given when given when given when given whenw", validationConfig);
        assertThat(acceptanceCriteriaValidatorEntry.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(acceptanceCriteriaValidatorEntry.getViolations()).contains(new CriteriaViolation(ViolationType.CriteriaThenClauseViolation, ""));
    }


    @Test
    public void testPerformScorerAndRatesFail(@Injectable ScorerConfigCopy validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getRatingtreshold();
            result = 0.3334f;
        }};

        AcceptanceCriteriaValidatorEntry ae = AcceptanceCriteriaScorer.performScorer("givengivengivengivengivengivengivengivengivengiven", validationConfig);
        System.out.println(ae.getPointsValuation());
        assertThat(ae.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerAndRatesSuccess(@Injectable ScorerConfigCopy validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getRatingtreshold();
            result = 0.3333f;
        }};

        AcceptanceCriteriaValidatorEntry ae = AcceptanceCriteriaScorer.performScorer("givengivengivengivengivengivengivengivengivengiven", validationConfig);
        System.out.println(ae.getPointsValuation());
        assertThat(ae.getRating()).isEqualTo(Rating.SUCCES);
    }
}
