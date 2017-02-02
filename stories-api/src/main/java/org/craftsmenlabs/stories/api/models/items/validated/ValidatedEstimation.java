package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@NoArgsConstructor
public class ValidatedEstimation extends AbstractValidatedItem<Float> {
    @Builder
    public ValidatedEstimation(float pointsValuation, List<Violation> violations, Rating rating, Float item) {
        super(pointsValuation, violations, rating, item);
    }
}