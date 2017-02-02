package org.craftsmenlabs.stories.api.models.items.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractValidatedItem<T> {
    private List<Violation> violations;
    private Rating rating;
    private T item;

    private float scoredPercentage;
    private float missedPercentage;
    private float scoredPoints;
    private float missedPoints;
}
