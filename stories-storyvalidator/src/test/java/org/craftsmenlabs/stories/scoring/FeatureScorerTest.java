package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FeatureScorerTest {

    private FeatureScorer getScorer(ValidationConfig validationConfig) {
        return new FeatureScorer(1f, validationConfig);
    }

    @Test
    public void performScorer_ReturnsZeroOnNullIssue(@Injectable ValidationConfig validationConfig) {
        new Expectations(){{
            validationConfig.getStory().isActive();
            result = true;

            validationConfig.getCriteria().isActive();
            result = true;

            validationConfig.getEstimation().isActive();
            result = true;
        }};

        final ValidatedFeature validatedFeature = getScorer(validationConfig).validate(null);
        float score = validatedFeature.getScoredPoints();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
        assertThat(validatedFeature.getAllViolations()).hasSize(7);
    }

    @Test
    public void performScorer_ReturnsZeroOnNullUserStory(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        new Expectations(){{
           feature.getUserstory();
           result = null;

            validationConfig.getStory().isActive();
            result = true;

            validationConfig.getCriteria().isActive();
            result = false;

            validationConfig.getEstimation().isActive();
            result = false;
        }};

        final ValidatedFeature validatedFeature = getScorer(validationConfig).validate(feature);
        float score = validatedFeature.getScoredPoints();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
        assertThat(validatedFeature.getAllViolations()).hasSize(1);
    }

    @Test
    public void performScorer_ReturnsZeroOnNullCriteria(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        new Expectations(){{
            feature.getUserstory();
            result = "";

            feature.getAcceptanceCriteria();
           result = null;


            validationConfig.getStory().isActive();
            result = true;

            validationConfig.getCriteria().isActive();
            result = true;

            validationConfig.getEstimation().isActive();
            result = true;
        }};

        final ValidatedFeature validatedFeature = getScorer(validationConfig).validate(feature);
        float score = validatedFeature.getScoredPoints();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
        assertThat(validatedFeature.getAllViolations()).hasSize(7);
    }

    @Test
    public void performScorer_ReturnsZeroOnAllNotActive(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        new Expectations(){{
            validationConfig.getStory().isActive();
            result = false;

            validationConfig.getCriteria().isActive();
            result = false;

            validationConfig.getEstimation().isActive();
            result = false;

        }};
        final ValidatedFeature validatedFeature = getScorer(validationConfig).validate(feature);
        float score = validatedFeature.getScoredPoints();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
        assertThat(validatedFeature.getAllViolations()).hasSize(0);
    }

    @Test
    public void performScorer_ReturnsOneOnOnlyPerfectUerstoryActive(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {

        ValidatedUserStory validatedUserStory1 = ValidatedUserStory.builder().scoredPoints(1f).build();

        new Expectations(){{
            feature.getUserstory();
            result = "as a i want so that as a i want so that as a i want so that as a i want so that ";

            validationConfig.getStory().isActive();
            result = true;

            validationConfig.getCriteria().isActive();
            result = false;

            validationConfig.getEstimation().isActive();
            result = false;

            validationConfig.getStory().getAsKeywords();
            result = "as a";

            validationConfig.getStory().getSoKeywords();
            result = "so";

            validationConfig.getStory().getIKeywords();
            result = "i";
        }};

        final ValidatedFeature validatedFeature = getScorer(validationConfig).validate(feature);
        float score = validatedFeature.getScoredPoints();
        assertThat(score).isCloseTo(1f, withinPercentage(1));
        assertThat(validatedFeature.getAllViolations()).hasSize(0);
    }
}
