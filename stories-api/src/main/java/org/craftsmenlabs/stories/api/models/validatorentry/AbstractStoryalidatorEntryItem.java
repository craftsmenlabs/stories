package org.craftsmenlabs.stories.api.models.validatorentry;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;


public abstract class AbstractStoryalidatorEntryItem<T> extends AbstractScorable {
    private T item;

    public AbstractStoryalidatorEntryItem() {
    }

    public AbstractStoryalidatorEntryItem(T item) {
        this.item = item;
    }


    public AbstractStoryalidatorEntryItem(float pointsValuation, List<Violation> violations, Rating rating, T item) {
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
