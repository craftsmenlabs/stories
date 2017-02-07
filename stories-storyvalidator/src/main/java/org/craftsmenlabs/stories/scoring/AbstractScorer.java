package org.craftsmenlabs.stories.scoring;

import lombok.AllArgsConstructor;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;


/**
 * Type for the scorers, which transforms a T (which should actually extend a scorable,
 * but doesn't because some scorables are actually just a Sting or a float for simplicities sake) to a validatedItem
 * @param <T>
 * @param <R>
 */
@AllArgsConstructor
public abstract class AbstractScorer<T, R extends AbstractValidatedItem> {
    protected float potentialPoints;
    protected ValidationConfig validationConfig;

    public abstract R validate(T item);


    public float getPotentialPoints() {
        return potentialPoints;
    }

    public void setPotentialPoints(float potentialPoints) {
        this.potentialPoints = potentialPoints;
    }
}
