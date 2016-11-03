package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
//@AllArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class AcceptanceCriteriaValidatorEntry extends AbstractValidatorEntry{
    private String acceptanceCriteria;

    @Builder
    public AcceptanceCriteriaValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, boolean isActive, String acceptanceCriteria) {
        super(pointsValuation, violations, rating, isActive);
        this.acceptanceCriteria = acceptanceCriteria;
        super.setPointsValuation (pointsValuation);
        super.setViolations (violations);
        super.setRating (rating);
        super.setActive(isActive);
    }
}