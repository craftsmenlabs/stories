package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
public class UserStoryValidatorEntry extends AbstractValidatorEntry{
    private String userStory;

    @Builder
    public UserStoryValidatorEntry(String userStory, float pointsValuation, List<Violation> violations, Rating rating, boolean isActive) {
        this.userStory = userStory;
        super.setPointsValuation(pointsValuation);
        super.setViolations(violations);
        super.setRating(rating);
        super.setActive(isActive);
    }
}