package org.craftsmenlabs.stories.scoring;

import lombok.AllArgsConstructor;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;

@AllArgsConstructor
public abstract class AbstractScorer<T extends Scorable, R extends AbstractValidatedItem> {
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
