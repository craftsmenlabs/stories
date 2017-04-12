package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Collections;
import java.util.List;


@Data
@NoArgsConstructor
public class ValidatedAcceptanceCriteria extends AbstractValidatedItem<String> {
    @Builder
    public ValidatedAcceptanceCriteria(List<Violation> violations, Rating rating, String item, double scoredPercentage, double missedPercentage, double scoredPoints, double missedPoints) {
        super(violations, rating, item, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
    }

    public static ValidatedAcceptanceCriteria empty(){
        return new ValidatedAcceptanceCriteria(Collections.emptyList(), Rating.FAIL, "", 0, 0, 0, 0);
    }
}