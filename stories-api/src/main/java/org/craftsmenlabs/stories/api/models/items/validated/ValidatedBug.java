package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
@JsonTypeName("BUG")
public class ValidatedBug extends ValidatedBacklogItem<Bug> {
    @Builder
    public ValidatedBug(List<Violation> violations, Rating rating, Bug bug, double scoredPercentage, double missedPercentage, double scoredPoints, double missedPoints) {
        super(violations, rating, bug, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
    }

    @Override
    public List<AbstractValidatedItem<?>> getSubItems() {
        return Collections.emptyList();
    }
}
