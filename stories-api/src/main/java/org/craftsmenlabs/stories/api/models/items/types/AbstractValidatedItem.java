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

    private double scoredPercentage;
    private double missedPercentage;
    private double scoredPoints;
    private double missedPoints;

    public void setPoints(double scoredPoints, double potentialPoints) {
        this.scoredPoints = scoredPoints;
        this.missedPoints = potentialPoints - scoredPoints;
        this.scoredPercentage = scoredPoints / potentialPoints;
        this.missedPercentage = missedPoints / potentialPoints;
    }
}
