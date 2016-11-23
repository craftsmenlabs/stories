package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimationValidatorEntry extends AbstractValidatorEntry {
    private Float estimation;

    @Builder
    public EstimationValidatorEntry(Float estimation, float pointsValuation, List<Violation> violations, Rating rating, boolean isActive) {
        super(pointsValuation, violations, rating, isActive);
        this.estimation = estimation;
    }

    @Override
    public String getRank() {
        return null;
    }

    @Override
    public String getType() {
        return "estimation";
    }
}