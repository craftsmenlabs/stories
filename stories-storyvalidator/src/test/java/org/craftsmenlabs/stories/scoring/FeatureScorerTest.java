package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class FeatureScorerTest {

    @Test
    public void performScorer_ReturnsZeroOnNullIssue(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        float score = IssueScorer.performScorer(null, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void performScorer_ReturnsZeroOnNullUserStory(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        new Expectations(){{
           feature.getUserstory();
           result = null;

        }};
        float score = IssueScorer.performScorer(feature, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void performScorer_ReturnsZeroOnNullCriteria(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        new Expectations(){{
            feature.getUserstory();
            result = "";

            feature.getAcceptanceCriteria();
           result = null;

        }};
        float score = IssueScorer.performScorer(feature, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void performScorer_ReturnsZeroOnAllNotActive(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        new Expectations(){{
            feature.getUserstory();
            result = "";

            validationConfig.getStory().isActive();
            result = false;

            feature.getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().isActive();
            result = false;


            feature.getEstimation();
            result = null;
            validationConfig.getEstimation().isActive();
            result = false;

        }};
        float score = IssueScorer.performScorer(feature, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Ignore(value = "TODO fix injecting expectations")
    @Test
    public void performScorer_ReturnsOneOnOnlyPerfectUerstoryActive(@Injectable Feature feature, @Injectable ValidationConfig validationConfig) {
        UserStoryValidatorEntry entry = UserStoryValidatorEntry.builder().pointsValuation(1f).userStory("").build();
        new Expectations(){{
            StoryScorer.performScorer("", validationConfig);
            result = entry;

            validationConfig.getStory().isActive();
            result = true;

            feature.getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().isActive();
            result = false;

            feature.getEstimation();
            result = null;

            validationConfig.getEstimation().isActive();
            result = false;

        }};
        float score = IssueScorer.performScorer(feature, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(1f, withinPercentage(1));
    }




}
