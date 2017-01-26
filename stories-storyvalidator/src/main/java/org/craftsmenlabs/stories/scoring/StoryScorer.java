package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoryScorer {
    public final static int USERSTORY_MINIMUM_LENGTH = 50;

    public static ValidatedUserStory performScorer(String userStory, ValidationConfig validationConfig) {

        List<Violation> violations = new ArrayList<>();

        final float STORYLENGTHCLAUSEPOINTS = 0.2f;
        final float STORYSOCLAUSEPOINTS = 0.4f;
        final float STORYICLAUSEPOINTS = 0.2f;
        float points = 0.0f;

        if (userStory == null || userStory.isEmpty())
        {
            violations.add(new Violation(ViolationType.StoryEmptyViolation, "This story is empty.", points));
        }else {
            final String userStoryLower = userStory.toLowerCase();


            if (userStoryLower.length() > USERSTORY_MINIMUM_LENGTH) {

                points += STORYLENGTHCLAUSEPOINTS;
            } else {
                violations.add(new Violation(
                        ViolationType.StoryFormatViolation,
                        "The story should contain a minimum length of " + USERSTORY_MINIMUM_LENGTH + " characters. " +
                        "It now contains " + userStory.length() + " characters.", STORYLENGTHCLAUSEPOINTS, 1f));
            }

            List<String> asKeywords = validationConfig.getStory().getAsKeywords() != null ? validationConfig.getStory().getAsKeywords() : Collections.emptyList();
            if (asKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                final float STORYASISCLAUSEPOINTS = 0.2f;
                points += STORYASISCLAUSEPOINTS;
            } else {
                violations.add(
                        new Violation(ViolationType.StoryFormatViolation,
                        "<As a> section is not described properly. The story should contain any of the following keywords: "+ String.join(", ", asKeywords),
                        STORYLENGTHCLAUSEPOINTS));
            }

            List<String> iKeywords = validationConfig.getStory().getIKeywords() != null ? validationConfig.getStory().getIKeywords() : Collections.emptyList();
            if (iKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += STORYICLAUSEPOINTS;
            } else {
                violations.add(new Violation(
                        ViolationType.StoryFormatViolation, "<I want> section is not described properly." +
                            "The story should contain any of the following keywords: "
                            + String.join(", ", iKeywords),
                        STORYICLAUSEPOINTS));
            }

            List<String> soKeywords = validationConfig.getStory().getSoKeywords() != null ? validationConfig.getStory().getSoKeywords() : Collections.emptyList();
            if (soKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += STORYSOCLAUSEPOINTS;
            } else {
                violations.add(new Violation(
                        ViolationType.StoryFormatViolation,
                        "<So that> section is not described properly." +
                            "The story should contain any of the following keywords: "
                            + String.join(", ", iKeywords),
                        STORYSOCLAUSEPOINTS));
            }
        }

        Rating rating = points >= validationConfig.getStory().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedUserStory
                .builder()
                .item(userStory)
                .pointsValuation(points)
                .violations(violations)
                .rating(rating)
                .build();
    }

}
