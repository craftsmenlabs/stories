package org.craftsmenlabs.stories.api.models.items.types;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

public abstract class AbstractValidatedItem<T> extends AbstractScorable {
    private T item;

    public AbstractValidatedItem() {
    }

    public AbstractValidatedItem(T item) {
        this.item = item;
    }


    public AbstractValidatedItem(float pointsValuation, List<Violation> violations, Rating rating, T item) {
        super(pointsValuation, violations, rating);
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
