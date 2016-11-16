package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class StoryScorerTest {

    @Test
    public void testPerformScorerReturnsZeroOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = "";

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = null;

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerMatchesAllKeywords(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getIssue().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingtreshold();
            result = 1f;
        }};

        UserStoryValidatorEntry entry1 = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig);
        assertThat(entry1.getPointsValuation()).isCloseTo(1.0f, withinPercentage(0.1));
        assertThat(entry1.getRating()).isEqualTo(Rating.SUCCESS);
    }


    @Test
    public void testPerformScorerDoesntMatchAsKeyword(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getIssue().getUserstory();
            result = "As super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchIKeyword(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getIssue().getUserstory();
            result = "As a super office user \n"
                    + "I want to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerDoesntMatchSoKeyword(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getIssue().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I have the most preferred alarm on top.";

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.6f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScoreStoryTooShort(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So");


            entry.getIssue().getUserstory();
            result = "AsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISo"
                    .substring(0, StoryScorer.USERSTORY_MINIMUM_LENGTH - 1);

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerStoryRightLength(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So");


            entry.getIssue().getUserstory();
            result = "AsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISo"
                    .substring(0, StoryScorer.USERSTORY_MINIMUM_LENGTH);

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.8f, withinPercentage(0.1));
    }

    @Test
    public void testPerformScorerReturnsFailOnLowScore(@Injectable IssueValidatorEntry entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getIssue().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingtreshold();
            result = 1.1f;
        }};

        Rating rating = StoryScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getRating();
        assertThat(rating).isEqualTo(Rating.FAIL);
    }
}
