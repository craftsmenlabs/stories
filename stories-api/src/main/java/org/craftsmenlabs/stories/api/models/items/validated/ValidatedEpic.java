package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@NoArgsConstructor
public class ValidatedEpic extends ValidatedBacklogItem<Epic> {
    @Builder
    public ValidatedEpic(float pointsValuation, List<Violation> violations, Rating rating, Epic epic) {
        super(pointsValuation, violations, rating, epic);
    }
}
