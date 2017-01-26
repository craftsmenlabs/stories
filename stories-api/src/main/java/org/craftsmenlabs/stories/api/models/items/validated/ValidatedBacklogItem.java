package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.types.Rankable;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ValidatedFeature.class, name = "FEATURE"),
        @JsonSubTypes.Type(value = ValidatedBug.class, name = "BUG"),
        @JsonSubTypes.Type(value = ValidatedEpic.class, name = "EPIC"),
        @JsonSubTypes.Type(value = ValidatedTeamTask.class, name = "TEAM_TASK")
})
//http://www.davismol.net/2015/03/05/jackson-json-deserialize-a-list-of-objects-of-subclasses-of-an-abstract-class/
public abstract class ValidatedBacklogItem<T extends Rankable> extends AbstractValidatedItem<T> implements Rankable {
    private float backlogPoints;
    private float potentialBacklogPoints;

    public ValidatedBacklogItem() {
    }

    public ValidatedBacklogItem(float pointsValuation, List<Violation> violations, Rating rating, T item) {
        super(pointsValuation, violations, rating, item);
    }

    @Override
    public String getRank() {
        return this.getItem().getRank();
    }

    public float getBacklogPoints() {
        return backlogPoints;
    }

    public void setBacklogPoints(float backlogPoints) {
        this.backlogPoints = backlogPoints;
    }

    public float getPotentialBacklogPoints() {
        return potentialBacklogPoints;
    }

    public void setPotentialBacklogPoints(float potentialBacklogPoints) {
        this.potentialBacklogPoints = potentialBacklogPoints;
    }
}