package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStoryValidatorEntry extends AbstractValidatorEntry{
    private String userStory;

    @Builder
    public UserStoryValidatorEntry(String userStory, float pointsValuation, List<Violation> violations, Rating rating, boolean isActive) {
        super(pointsValuation, violations, rating, isActive);
        this.userStory = userStory;
    }

    @Override
    public String getRank() {
        return null;
    }
}