package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public class ValidatedEstimation extends AbstractValidatedItem<Double> {
    @Builder
    public ValidatedEstimation(List<Violation> violations, Rating rating, Double item, double scoredPercentage, double missedPercentage, double scoredPoints, double missedPoints) {
        super(violations, rating, item, scoredPercentage, missedPercentage, scoredPoints, missedPoints );
    }

    public static ValidatedEstimation empty(){
        return ValidatedEstimation.builder()
                .violations(Collections.emptyList())
                .rating(Rating.FAIL)
                .item(0.0)
                .build();
    }
}