package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Criteria;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Collections;
import java.util.List;


@Data
@NoArgsConstructor
public class ValidatedAcceptanceCriteria extends AbstractValidatedItem<Criteria> {
    @Builder
    public ValidatedAcceptanceCriteria(List<Violation> violations, Rating rating, Criteria item, float scoredPercentage, float missedPercentage, float scoredPoints, float missedPoints) {
        super(violations, rating, item, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
    }

    public static ValidatedAcceptanceCriteria empty(){
        return new ValidatedAcceptanceCriteria(Collections.emptyList(), Rating.FAIL, Criteria.empty(), 0f, 0f, 0f, 0f);
    }
}