package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class StoryScorerTest {

    @Test
    public void testPerformScorerReturnsZeroOnEmpty(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getUserstory();
            result = "";

            validationConfig.getStory().getRatingThreshold();
            result = 0.7;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(1.0, validationConfig).validate(entry.getItem().getUserstory());
        double score = validatedUserStory.getScoredPoints();

        assertThat(score).isEqualTo(0.0);
        assertThat(validatedUserStory.getViolations()).hasSize(1);

        Violation expectedViolation = Violation.builder()
                .violationType(ViolationType.StoryEmptyViolation)
                .cause("This story is empty.")
                .missedPercentage(1.0)
                .scoredPercentage(0.0)
                .missedPoints(1.0)
                .scoredPoints(0.0)
                .build();
        assertThat(validatedUserStory.getViolations().get(0)).isEqualTo(expectedViolation);
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem().getUserstory();
            result = null;

            validationConfig.getStory().getRatingThreshold();
            result = 0.7;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(1.0, validationConfig).validate(entry.getItem().getUserstory());
        double score = validatedUserStory.getScoredPoints();
        assertThat(score).isEqualTo(0.0);
        assertThat(validatedUserStory.getViolations()).hasSize(1);

        Violation expectedViolation = Violation.builder()
                .violationType(ViolationType.StoryEmptyViolation)
                .cause("This story is empty.")
                .missedPercentage(1.0)
                .scoredPercentage(0.0)
                .missedPoints(1.0)
                .scoredPoints(0.0)
                .build();
        assertThat(validatedUserStory.getViolations().get(0)).isEqualTo(expectedViolation);
    }

    @Test
    public void testPerformScorerMatchesAllKeywords(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getItem().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 1.0;
        }};

        ValidatedUserStory validatedUserStory = new StoryScorer(1.0, validationConfig).validate(entry.getItem().getUserstory());
        assertThat(validatedUserStory.getScoredPoints()).isCloseTo(1.0, withinPercentage(0.1));
        assertThat(validatedUserStory.getRating()).isEqualTo(Rating.SUCCESS);
        assertThat(validatedUserStory.getViolations()).hasSize(0);

    }


    @Test
    public void testPerformScorerDoesntMatchAsKeyword(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getItem().getUserstory();
            result = "As super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 70.0;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(100.0, validationConfig).validate(entry.getItem().getUserstory());
        double score = validatedUserStory.getScoredPoints();
        assertThat(score).isCloseTo(75.0, withinPercentage(0.1));
        assertThat(validatedUserStory.getViolations()).hasSize(1);

        Violation expectedViolation = Violation.builder()
                .violationType(ViolationType.StoryAsIsClauseViolation)
                .cause("<As a> section is not described properly. The story should contain any of the following keywords: As a ")
                .missedPercentage(1.0)
                .scoredPercentage(0.0)
                .missedPoints(25.0)
                .scoredPoints(0.0)
                .build();
        assertThat(validatedUserStory.getViolations().get(0)).isEqualTo(expectedViolation);

    }

    @Test
    public void testPerformScorerDoesntMatchIKeyword(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getItem().getUserstory();
            result = "As a super office user \n"
                    + "I want to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 70.;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(90.0, validationConfig).validate(entry.getItem().getUserstory());
        double score = validatedUserStory.getScoredPoints();
        assertThat(score).isCloseTo(67.5, withinPercentage(0.1));
        assertThat(validatedUserStory.getViolations()).hasSize(1);
    }

    @Test
    public void testPerformScorerDoesntMatchSoKeyword(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getItem().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 70.;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(80.0, validationConfig).validate(entry.getItem().getUserstory());
        double score = validatedUserStory.getScoredPoints();
        assertThat(score).isCloseTo(60., withinPercentage(0.1));
        assertThat(validatedUserStory.getViolations()).hasSize(1);

        Violation expectedViolation = Violation.builder()
                .violationType(ViolationType.StorySoClauseViolation)
                .cause("<So that> section is not described properly. The story should contain any of the following keywords: So I can")
                .missedPercentage(1.0)
                .scoredPercentage(0.0)
                .missedPoints(20.0)
                .scoredPoints(0.0)
                .build();
        assertThat(validatedUserStory.getViolations().get(0)).isEqualTo(expectedViolation);
    }

    @Test
    public void testPerformScoreStoryTooShort(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So");


            entry.getItem().getUserstory();
            result = "AsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISo"
                    .substring(0, StoryScorer.USERSTORY_MINIMUM_LENGTH - 1);

            validationConfig.getStory().getRatingThreshold();
            result = 70.;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(100.0, validationConfig).validate(entry.getItem().getUserstory());
        double score = validatedUserStory.getScoredPoints();
        assertThat(score).isCloseTo(75., withinPercentage(0.1));
        assertThat(validatedUserStory.getViolations()).hasSize(1);


        Violation expectedViolation = Violation.builder()
                .violationType(ViolationType.StoryLengthClauseViolation)
                .cause("The story should contain a minimum length of 50 characters. It now contains 49 characters.")
                .missedPercentage(1.0)
                .scoredPercentage(0.0)
                .missedPoints(25.0)
                .scoredPoints(0.0)
                .build();
        assertThat(validatedUserStory.getViolations().get(0)).isEqualTo(expectedViolation);
    }

    @Test
    public void testPerformScorerStoryRightLength(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So");


            entry.getItem().getUserstory();
            result = "AsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISoAsISo"
                    .substring(0, StoryScorer.USERSTORY_MINIMUM_LENGTH);

            validationConfig.getStory().getRatingThreshold();
            result = 70;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(3.00, validationConfig).validate(entry.getItem().getUserstory());
        assertThat(validatedUserStory.getScoredPoints()).isCloseTo(3.0, withinPercentage(0.1));
        assertThat(validatedUserStory.getMissedPoints()).isCloseTo(0.0, withinPercentage(0.1));
        assertThat(validatedUserStory.getMissedPercentage()).isCloseTo(0.0, withinPercentage(0.1));
        assertThat(validatedUserStory.getScoredPercentage()).isCloseTo(100.0, withinPercentage(0.1));
        assertThat(validatedUserStory.getViolations()).hasSize(0);
        assertThat(validatedUserStory.getRating()).isEqualTo(Rating.SUCCESS);

    }

    @Test
    public void testPerformScorerReturnsFailOnLowScore(@Injectable ValidatedFeature entry, @Injectable ValidationConfig validationConfig) {
        new Expectations() {{
            validationConfig.getStory().getAsKeywords();
            result = Arrays.asList("As a ");
            validationConfig.getStory().getIKeywords();
            result = Arrays.asList("I would like to ");
            validationConfig.getStory().getSoKeywords();
            result = Arrays.asList("So I can");


            entry.getItem().getUserstory();
            result = "As a super office user \n"
                    + "I would like to be informed about the alarms in my user \\n\"\n"
                    + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingThreshold();
            result = 100.1;
        }};

        final ValidatedUserStory validatedUserStory = new StoryScorer(1.0, validationConfig).validate(entry.getItem().getUserstory());
        Rating rating = validatedUserStory.getRating();
        assertThat(rating).isEqualTo(Rating.FAIL);
        assertThat(validatedUserStory.getViolations()).hasSize(0);
    }
}
