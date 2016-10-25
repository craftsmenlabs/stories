package org.craftsmenlabs.stories.api.models.validatorentry;

import java.util.List;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;
import lombok.*;

@Data
//@AllArgsConstructor
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