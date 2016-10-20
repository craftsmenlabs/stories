package org.craftsmenlabs.stories.scoring;

import java.util.ArrayList;
import java.util.List;
import org.craftsmenlabs.stories.api.models.*;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;

public class StoryScorer {

    public static UserStoryValidatorEntry performScorer(String userStory, ScorerConfigCopy validationConfig) {

        List<Violation> violations = new ArrayList<>();

        float points = 0.0f;

        if (userStory == null || userStory.isEmpty())
        {
            violations.add(new StoryViolation(ViolationType.StoryEmptyViolation, "This story is empty."));
        }else {
            userStory = userStory.toLowerCase();
            if (userStory.length() > 20) {
                points += 0.2f;
            } else {
                violations.add(new StoryViolation(ViolationType.StoryLengthClauseViolation, "Story is to short."));
            }
            if (userStory.contains("as a")) {
                points += 0.2f;
            } else {
                violations.add(new StoryViolation(ViolationType.StoryAsIsClauseViolation, "<As a> section is not described properly."));
            }
            if (userStory.contains("i")) {
                points += 0.2f;
            } else {
                violations.add(new StoryViolation(ViolationType.StoryIClauseViolation, "<I want> section is not described properly."));
            }
            if (userStory.contains("so")) {
                points += 0.4f;
            } else {
                violations.add(new StoryViolation(ViolationType.StorySoClauseViolation, "<So that> section is not described properly."));
            }
        }

        Rating rating = points >= validationConfig.getStory().getRatingtreshold() ? Rating.SUCCES : Rating.FAIL;

        return UserStoryValidatorEntry
                .builder()
                .userStory(userStory)
                .pointsValuation(points)
                .violations(violations)
                .rating(rating)
                .build();
    }

}
