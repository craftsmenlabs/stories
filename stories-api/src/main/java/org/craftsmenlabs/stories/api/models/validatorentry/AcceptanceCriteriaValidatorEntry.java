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
public class AcceptanceCriteriaValidatorEntry extends AbstractValidatorEntry{
    private String acceptanceCriteria;

    @Builder
    public AcceptanceCriteriaValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, boolean isActive, String acceptanceCriteria) {
        super(pointsValuation, violations, rating, isActive);
        this.acceptanceCriteria = acceptanceCriteria;
    }

    @Override
    public String getRank() {
        return null;
    }

    @Override
    public String getType() {
        return "acceptance criteria";
    }
}