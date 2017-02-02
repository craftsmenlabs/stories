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
    public ValidatedEstimation(List<Violation> violations, Rating rating, Float item, float scoredPercentage, float missedPercentage, float scoredPoints, float missedPoints) {
        super(violations, rating, item, scoredPercentage, missedPercentage, scoredPoints, missedPoints );
    }
}