package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Epic;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpicValidatorEntry extends BacklogItem{
    private Epic epic;

    @Builder
    public EpicValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, Epic epic) {
        super(pointsValuation, violations, rating);
        this.epic = epic;
    }

    @Override
    public String getRank() {
        return epic.getRank();
    }

    @Override
    public ValidatorEntryType getType() {
        return ValidatorEntryType.EPIC;
    }
}
