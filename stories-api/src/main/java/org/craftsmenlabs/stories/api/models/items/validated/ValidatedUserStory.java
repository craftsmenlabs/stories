package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Story;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ValidatedUserStory extends AbstractValidatedItem<Story> {

    @Builder
    public ValidatedUserStory(List<Violation> violations, Rating rating, Story item, float scoredPercentage, float missedPercentage, float scoredPoints, float missedPoints) {
        super(violations, rating, item, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
    }

    public static ValidatedUserStory empty(){
        return new ValidatedUserStory(Collections.emptyList(), Rating.FAIL, Story.empty(), 0f, 0f, 0f, 0f);
    }
}