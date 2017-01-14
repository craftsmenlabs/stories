package org.craftsmenlabs.stories.api.models.items.validated;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@NoArgsConstructor
@Data
public class ValidatedBug extends ValidatedBacklogItem<Bug> {
    @Builder
    public ValidatedBug(float pointsValuation, List<Violation> violations, Rating rating, Bug bug) {
        super(pointsValuation, violations, rating, bug);
    }
}
