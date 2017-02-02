package org.craftsmenlabs.stories.api.models.items.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractValidatedItem<T> {
    private float pointsValuation = 0.0f;
    private List<Violation> violations;
    private Rating rating;

    private T item;
}
