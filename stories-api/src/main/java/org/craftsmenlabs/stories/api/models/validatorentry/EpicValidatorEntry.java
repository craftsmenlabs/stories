package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EpicValidatorEntry extends AbstractValidatorEntry {
    @JsonProperty("epic")
    private Epic epic;

    @Builder
    public EpicValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, boolean isActive, Epic epic) {
        super(pointsValuation, violations, rating, isActive);
        this.epic = epic;
    }

    @Override
    public String getRank() {
        return epic.getRank();
    }

    @Override
    public String getType() {
        return "epic";
    }
}
