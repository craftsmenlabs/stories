package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.types.Rankable;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ValidatedFeature.class, name = "FEATURE"),
        @JsonSubTypes.Type(value = ValidatedBug.class, name = "BUG"),
        @JsonSubTypes.Type(value = ValidatedEpic.class, name = "EPIC"),
        @JsonSubTypes.Type(value = ValidatedTeamTask.class, name = "TEAM_TASK")
})
@EqualsAndHashCode(callSuper = true)
//http://www.davismol.net/2015/03/05/jackson-json-deserialize-a-list-of-objects-of-subclasses-of-an-abstract-class/
public abstract class ValidatedBacklogItem<T extends Rankable> extends AbstractValidatedItem<T> implements Rankable {

    public ValidatedBacklogItem() {
    }

    public ValidatedBacklogItem(List<Violation> violations, Rating rating, T item, double scoredPercentage, double missedPercentage, double scoredPoints, double missedPoints) {
        super(violations, rating, item, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
    }

    @Override
    public String getRank() {
        return this.getItem().getRank();
    }

    public abstract List<AbstractValidatedItem<?>> getSubItems();

    public List<Violation> getAllViolations(){
        return Stream.of(getViolations(),
                getSubItems().stream()
                    .filter(entry -> entry != null && entry.getViolations() != null)
                    .map(AbstractValidatedItem::getViolations)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}