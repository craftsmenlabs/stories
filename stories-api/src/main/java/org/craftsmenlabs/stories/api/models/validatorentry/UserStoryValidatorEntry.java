package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserStoryValidatorEntry {
    private String userStory;
    private float pointsValuation = 0.0f;
    private List<Violation> violations;
    private Rating rating;
}