package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class AcceptanceCriteriaScorerTest {

    private String goodCriteria =
            "Given I have 100 shares of MSFT stock\n" +
                    "And I have 150 shares of APPL stock\n" +
                    "And the time is before close of trading\n" +
                    "When I ask to sell 20 shares of MSFT stock\n" +
                    "Then I should have 80 shares of MSFT stock";


    @Test
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        List<Violation> v = new ArrayList<>();
        new Expectations() {{
            entry.getItem().getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;

        }};

        float score = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getAcceptanceCriteria();
            result = null;


            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        float score = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerAddsGivenClauseViolationOnNoGiven(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("gooooven ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(ViolationType.CriteriaGivenClauseViolation, "<Given> section is not described properly. The criteria should contain any of the following keywords: gooooven "));
    }

    @Test
    public void testPerformScorerAddsWhenClauseViolationOnNoGiven(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("whooon ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(ViolationType.CriteriaWhenClauseViolation, "<When> section is not described properly. The criteria should contain any of the following keywords: whooon "));
    }

    @Test
    public void testPerformScorerAddsThenClauseViolationOnNoGiven(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("thooon ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.33333f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(ViolationType.CriteriaThenClauseViolation, "<Then> section is not described properly. The criteria should contain any of the following keywords: thooon "));
    }

    @Test
    public void testPerformScorerAndRatesFail(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.3334f;
        }};

        ValidatedAcceptanceCriteria ae = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(ae.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerAndRatesSuccess(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.3333f;

        }};

        ValidatedAcceptanceCriteria ae = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(ae.getRating()).isEqualTo(Rating.SUCCESS);
    }

    @Test
    public void testPerformScorerMatchesAllKeywords(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(0.0001));
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
    }


    @Test
    public void testPerformScorerDoesntMatchGivenKeyword(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("goooven ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.6f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.6666f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchWhenKeyword(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("whoooooon ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.6666f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchThenKeyword(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("thoooon ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.6666f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScoreCriteriaTooShort(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            entry.getItem().getAcceptanceCriteria();
            result = "given when then given when then given when then ".substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA - 1);

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.0f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerCriteriaRightLength(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");

            entry.getItem().getAcceptanceCriteria();
            result = "given when then given when then given when then ".substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA);

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        ValidatedAcceptanceCriteria entry1 = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerReturnsFailOnLowScore(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 1.1f;
        }};

        Rating rating = AcceptanceCriteriaScorer.performScorer(entry.getItem().getAcceptanceCriteria(), validationConfig).getRating();
        assertThat(rating).isEqualTo(Rating.FAIL);
    }

}
