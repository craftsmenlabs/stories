package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.FeatureValidatorEntry;
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
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        List<Violation> v = new ArrayList<>();
        new Expectations() {{
            entry.getFeature().getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;

        }};

        float score = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getFeature().getAcceptanceCriteria();
            result = null;


            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        float score = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }

    @Test
    public void testPerformScorerAddsGivenClauseViolationOnNoGiven(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("gooooven ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.25f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(
                ViolationType.CriteriaGivenClauseViolation,
                "<Given> section is not described properly. The criteria should contain any of the following keywords: gooooven ",
                0.25f
                ));
    }

    @Test
    public void testPerformScorerAddsWhenClauseViolationOnNoGiven(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("whooon ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.25f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(
                ViolationType.CriteriaWhenClauseViolation,
                "<When> section is not described properly. The criteria should contain any of the following keywords: whooon ",
                0.25f));
    }

    @Test
    public void testPerformScorerAddsThenClauseViolationOnNoGiven(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("thooon ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f - 0.25f, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(
                ViolationType.CriteriaThenClauseViolation,
                "<Then> section is not described properly. The criteria should contain any of the following keywords: thooon ",
                0.25f));
    }

    @Test
    public void testPerformScorerAndRatesFail(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.75f;
        }};

        AcceptanceCriteriaValidatorEntry ae = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(ae.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerAndRatesSuccess(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig)
    {
        new Expectations()
        {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.3333f;

        }};

        AcceptanceCriteriaValidatorEntry ae = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(ae.getRating()).isEqualTo(Rating.SUCCESS);
    }

    @Test
    public void testPerformScorerMatchesAllKeywords(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.9999f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(0.0001));
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
    }


    @Test
    public void testPerformScorerDoesntMatchGivenKeyword(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("goooven ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.6f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.75f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchWhenKeyword(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("whoooooon ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.75f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchThenKeyword(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("thoooon ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.75f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScoreCriteriaTooShort(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            entry.getFeature().getAcceptanceCriteria();
            result = "given when then given when then given when then ".substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA - 1);

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(0.0f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerCriteriaRightLength(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");

            entry.getFeature().getAcceptanceCriteria();
            result = "given when then given when then given when then ".substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA);

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7f;
        }};

        AcceptanceCriteriaValidatorEntry entry1 = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerReturnsFailOnLowScore(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");
            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            entry.getFeature().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 1.1f;
        }};

        Rating rating = AcceptanceCriteriaScorer.performScorer(entry.getFeature().getAcceptanceCriteria(), validationConfig).getRating();
        assertThat(rating).isEqualTo(Rating.FAIL);
    }

}
