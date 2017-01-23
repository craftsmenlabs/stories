package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@NoArgsConstructor
public class ValidatedUserStory extends AbstractValidatedItem<String> {
    @Builder
    public ValidatedUserStory(float pointsValuation, List<Violation> violations, Rating rating, String item) {
        super(pointsValuation, violations, rating, item);
    }
}