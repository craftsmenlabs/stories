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

@SuppressWarnings({"ResultOfMethodCallIgnored"})
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
            result = 0.7;

        }};

        double score = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria()).getScoredPoints();
        assertThat(score).isCloseTo(0.0, withinPercentage(1));
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getAcceptanceCriteria();
            result = null;


            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7;
        }};

        double score = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria()).getScoredPoints();
        assertThat(score).isCloseTo(0.0, withinPercentage(1));
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
            result = 0.9999;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(1.0 - 0.25, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(
                ViolationType.CriteriaGivenClauseViolation,
                "<Given> section is not described properly. The criteria should contain any of the following keywords: gooooven ",
                0.25
        ));
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
            result = 0.9999;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(1.0 - 0.25, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(
                ViolationType.CriteriaWhenClauseViolation,
                "<When> section is not described properly. The criteria should contain any of the following keywords: whooon ",
                0.25));
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
            result = 0.9999;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(1.0 - 0.25, withinPercentage(1));
        assertThat(entry1.getViolations()).contains(new Violation(
                ViolationType.CriteriaThenClauseViolation,
                "<Then> section is not described properly. The criteria should contain any of the following keywords: thooon ",
                0.25));
    }

    @Test
    public void testPerformScorerAndRatesFail(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.75;
        }};

        ValidatedAcceptanceCriteria ae = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(ae.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerAndRatesSuccess(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");

            entry.getItem().getAcceptanceCriteria();
            result = goodCriteria;

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.3333;

        }};

        ValidatedAcceptanceCriteria ae = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
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
            result = 0.9999;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(0.05, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(0.05, withinPercentage(0.0001));
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
            result = 0.6;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(0.05, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(0.0375, withinPercentage(0.1));
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
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
            result = 0.7;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(0.75, withinPercentage(0.1));
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
            result = 0.7;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(0.75, withinPercentage(0.1));
    }

    @Test
    public void testPerformScoreCriteriaTooShort(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            entry.getItem().getAcceptanceCriteria();
            result = "given when then given when then given when then ".substring(0, AcceptanceCriteriaScorer.MINIMUM_LENGTH_OF_ACC_CRITERIA - 1);

            validationConfig.getCriteria().getRatingThreshold();
            result = 0.7;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(0.0, withinPercentage(0.1));
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
            result = 0.7;
        }};

        ValidatedAcceptanceCriteria entry1 = new AcceptanceCriteriaScorer(1.0, validationConfig).validate(entry.getItem().getAcceptanceCriteria());
        assertThat(entry1.getScoredPoints()).isCloseTo(1.0, withinPercentage(0.1));
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
            result = 1.1;
        }};

        Rating rating = new AcceptanceCriteriaScorer(0.01, validationConfig).validate(entry.getItem().getAcceptanceCriteria()).getRating();
        assertThat(rating).isEqualTo(Rating.FAIL);
    }

}
