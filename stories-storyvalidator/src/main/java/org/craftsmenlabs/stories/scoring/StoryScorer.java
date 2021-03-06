package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoryScorer extends AbstractScorer<String, ValidatedUserStory >{
    public final static int USERSTORY_MINIMUM_LENGTH = 50;
    private final double potentialPoints;

    public StoryScorer(double potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
        this.potentialPoints = potentialPoints;
    }

    @Override
    public ValidatedUserStory validate(String userStory) {
        List<Violation> violations = new ArrayList<>();

        final double STORY_LENGTH_CLAUSE_POINTS = potentialPoints / 4;
        final double STORY_AS_A_CLAUSE_POINTS = potentialPoints / 4;
        final double STORY_SO_CLAUSE_POINTS = potentialPoints / 4;
        final double STORY_I_CLAUSE_POINTS = potentialPoints / 4;

        double points = 0.0;

        if (userStory == null || StringUtils.isEmpty(userStory))
        {
            violations.add(new Violation(ViolationType.StoryEmptyViolation, "This story is empty.", potentialPoints));
        } else {
            final String userStoryLower = userStory.toLowerCase();

            if (userStoryLower.length() >= USERSTORY_MINIMUM_LENGTH) {
                points += STORY_LENGTH_CLAUSE_POINTS;
            } else {
                violations.add(new Violation(
                        ViolationType.StoryLengthClauseViolation,
                        "The story should contain a minimum length of " + USERSTORY_MINIMUM_LENGTH + " characters. " +
                        "It now contains " + userStory.length() + " characters.", STORY_LENGTH_CLAUSE_POINTS));
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
                        "<So that> section is not described properly. " +
                            "The story should contain any of the following keywords: "
                            + String.join(", ", soKeywords),
                        STORY_SO_CLAUSE_POINTS));
            }
        }


        final ValidatedUserStory validatedUserStory = ValidatedUserStory
                .builder()
                .item(userStory)
                .violations(violations)
                .build();

        validatedUserStory.setPoints(points, potentialPoints);
        Rating rating = validatedUserStory.getScoredPercentage() >= validationConfig.getStory().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;
        validatedUserStory.setRating(rating);

        return validatedUserStory;
    }

}
