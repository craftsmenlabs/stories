package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Story;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoryScorer extends AbstractScorer<Story, ValidatedUserStory >{
    public final static int USERSTORY_MINIMUM_LENGTH = 50;
    private final float potentialPoints;

    public StoryScorer(float potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
        this.potentialPoints = potentialPoints;
    }

    @Override
    public ValidatedUserStory validate(Story userStory) {
        List<Violation> violations = new ArrayList<>();

        final float STORY_LENGTH_CLAUSE_POINTS = potentialPoints/ 4f;
        final float STORY_AS_A_CLAUSE_POINTS = potentialPoints/ 4f;
        final float STORY_SO_CLAUSE_POINTS = potentialPoints/ 4f;
        final float STORY_I_CLAUSE_POINTS = potentialPoints/ 4f;

        float points = 0.0f;

        if (userStory == null || StringUtils.isEmpty(userStory.getStory()))
        {
            violations.add(new Violation(ViolationType.StoryEmptyViolation, "This story is empty.", 1f, 1f));
        } else {
            final String userStoryLower = userStory.getStory().toLowerCase();

            if (userStoryLower.length() > USERSTORY_MINIMUM_LENGTH) {
                points += STORY_LENGTH_CLAUSE_POINTS;
            } else {
                violations.add(new Violation(
                        ViolationType.StoryLengthClauseViolation,
                        "The story should contain a minimum length of " + USERSTORY_MINIMUM_LENGTH + " characters. " +
                        "It now contains " + userStory.getStory().length() + " characters.", STORY_LENGTH_CLAUSE_POINTS));
            }

            List<String> asKeywords = validationConfig.getStory().getAsKeywords() != null ? validationConfig.getStory().getAsKeywords() : Collections.emptyList();
            if (asKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += STORY_AS_A_CLAUSE_POINTS;
            } else {
                violations.add(
                        new Violation(ViolationType.StoryAsIsClauseViolation,
                        "<As a> section is not described properly. The story should contain any of the following keywords: "+ String.join(", ", asKeywords),
                                STORY_AS_A_CLAUSE_POINTS));
            }

            List<String> iKeywords = validationConfig.getStory().getIKeywords() != null ? validationConfig.getStory().getIKeywords() : Collections.emptyList();
            if (iKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += STORY_I_CLAUSE_POINTS;
            } else {
                violations.add(new Violation(
                        ViolationType.StoryIClauseViolation, "<I want> section is not described properly." +
                            "The story should contain any of the following keywords: "
                            + String.join(", ", iKeywords),
                        STORY_I_CLAUSE_POINTS));
            }

            List<String> soKeywords = validationConfig.getStory().getSoKeywords() != null ? validationConfig.getStory().getSoKeywords() : Collections.emptyList();
            if (soKeywords.stream().anyMatch(s -> userStoryLower.contains(s.toLowerCase()))) {
                points += STORY_SO_CLAUSE_POINTS;
            } else {
                violations.add(new Violation(
                        ViolationType.StorySoClauseViolation,
                        "<So that> section is not described properly." +
                            "The story should contain any of the following keywords: "
                            + String.join(", ", iKeywords),
                        STORY_SO_CLAUSE_POINTS));
            }
        }

        Rating rating = points >= validationConfig.getStory().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedUserStory
                .builder()
                .item(userStory)
                .violations(violations)
                .rating(rating)
                .scoredPoints(points)
                .missedPoints(potentialPoints - points)
                .scoredPercentage(points / potentialPoints)
                .missedPercentage(1f - (points / potentialPoints))
                .build();
    }

}
