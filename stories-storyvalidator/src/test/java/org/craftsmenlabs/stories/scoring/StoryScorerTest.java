package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.FeatureValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class StoryScorerTest {

    @Test
    public void testPerformScorerReturnsZeroOnEmpty(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getFeature().getUserstory();
            result = "";

            validationConfig.getStory().getRatingThreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getFeature().getUserstory();
            result = null;

            validationConfig.getStory().getRatingThreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerMatchesAllKeywords(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getFeature().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 1f;
        }};

        UserStoryValidatorEntry entry1 = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f, withinPercentage(0.1));
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
    }


    @Test
    public void testPerformScorerDoesntMatchAsKeyword(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getFeature().getUserstory();
            result = "As super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchIKeyword(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getFeature().getUserstory();
            result = "As a super office user \n"
                    + "I want to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchSoKeyword(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getFeature().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.6f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScoreStoryTooShort(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So");


            entry.getFeature().getUserstory();
            result = "AsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISo"
                    .substring(0, StoryScorer.USERSTORY_MINIMUM_LENGTH - 1);

            validationConfig.getStory().getRatingThreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerStoryRightLength(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So");


            entry.getFeature().getUserstory();
            result = "AsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISo"
                    .substring(0, StoryScorer.USERSTORY_MINIMUM_LENGTH);

            validationConfig.getStory().getRatingThreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerReturnsFailOnLowScore(@Injectable FeatureValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getFeature().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 1.1f;
        }};

        Rating rating = StoryScorer.performScorer(entry.getFeature().getUserstory(), validationConfig).getRating();
        assertThat(rating).isEqualTo(Rating.FAIL);
    }
}
