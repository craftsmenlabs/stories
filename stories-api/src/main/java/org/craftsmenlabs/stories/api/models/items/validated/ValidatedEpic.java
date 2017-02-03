package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@NoArgsConstructor
@JsonTypeName("EPIC")
public class ValidatedEpic extends ValidatedBacklogItem<Epic> {
    @Builder
    public ValidatedEpic(List<Violation> violations, Rating rating, Epic epic, float scoredPercentage, float missedPercentage, float scoredPoints, float missedPoints) {
        super(violations, rating, epic, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
    }
}
