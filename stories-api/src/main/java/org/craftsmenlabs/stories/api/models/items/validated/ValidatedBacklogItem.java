package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.TeamTask;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.types.Rankable;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ValidatedFeature.class),
        @JsonSubTypes.Type(value = ValidatedBug.class),
        @JsonSubTypes.Type(value = ValidatedEpic.class),
        @JsonSubTypes.Type(value = TeamTask.class)
})
//http://www.davismol.net/2015/03/05/jackson-json-deserialize-a-list-of-objects-of-subclasses-of-an-abstract-class/
public abstract class ValidatedBacklogItem<T extends Rankable> extends AbstractValidatedItem<T> implements Rankable {

    public ValidatedBacklogItem() {
    }

    public ValidatedBacklogItem(float pointsValuation, List<Violation> violations, Rating rating, T item) {
        super(pointsValuation, violations, rating, item);
    }

    @Override
    public String getRank() {
        return this.getItem().getRank();
    }
}