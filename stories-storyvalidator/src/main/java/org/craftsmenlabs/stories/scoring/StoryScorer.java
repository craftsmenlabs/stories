package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StoryScorer {
    public final static int USERSTORY_MINIMUM_LENGTH = 50;

    public static UserStoryValidatorEntry performScorer(String userStory, ValidationConfig validationConfig) {

        List<Violation> violations = new ArrayList<>();

        float points = 0.0f;

        if (userStory == null || userStory.isEmpty())
        {
            violations.add(new Violation(ViolationType.StoryEmptyViolation, "This story is empty."));
        }else {
            final String userStoryLower = userStory.toLowerCase();


            if (userStoryLower.length() > USERSTORY_MINIMUM_LENGTH) {
                points += 0.2f;
            } else {
                violations.add(new Violation(ViolationType.StoryLengthClauseViolation, "The story should contain a minimum length of " + USERSTORY_MINIMUM_LENGTH + " characters. " +
                        "It now contains " + userStory.length() + " characters."));
            }

            List<String> asKeywords = validationConfig.getStory().getAsKeywords() != null ? validationConfig.getStory().getAsKeywords() : Collections.emptyList();
            if (asKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += 0.2f;
            } else {
                violations.add(new Violation(ViolationType.StoryAsIsClauseViolation, "<As a> section is not described properly." +
                        "The story should contain any of the following keywords: "
                        + String.join(", ", validationConfig.getStory().getAsKeywords())));
            }

            List<String> iKeywords = validationConfig.getStory().getIKeywords() != null ? validationConfig.getStory().getIKeywords() : Collections.emptyList();
            if (iKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += 0.2f;
            } else {
                violations.add(new Violation(ViolationType.StoryIClauseViolation, "<I want> section is not described properly." +
                        "The story should contain any of the following keywords: "
                        + String.join(", ", validationConfig.getStory().getIKeywords())));
            }

            List<String> soKeywords = validationConfig.getStory().getSoKeywords() != null ? validationConfig.getStory().getSoKeywords() : Collections.emptyList();
            if (soKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += 0.4f;
            } else {
                violations.add(new Violation(ViolationType.StorySoClauseViolation, "<So that> section is not described properly." +
                        "The story should contain any of the following keywords: "
                        + String.join(", ", validationConfig.getStory().getSoKeywords())));
            }
        }

        Rating rating = points >= validationConfig.getStory().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return UserStoryValidatorEntry
                .builder()
                .item(userStory)
                .pointsValuation(points)
                .violations(violations)
                .rating(rating)
                .build();
    }

}
