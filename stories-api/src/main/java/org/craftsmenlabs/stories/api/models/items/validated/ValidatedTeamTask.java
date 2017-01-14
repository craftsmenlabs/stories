package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.TeamTask;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedTeamTask extends ValidatedBacklogItem<TeamTask> {
    @JsonProperty("acceptanceCriteriaValidatorEntry")
    private ValidatedAcceptanceCriteria validatedAcceptanceCriteria;

    @JsonProperty("estimationValidatorEntry")
    private ValidatedEstimation validatedEstimation;

    @Builder
    public ValidatedTeamTask(float pointsValuation, List<Violation> violations, Rating rating, TeamTask teamTask, ValidatedAcceptanceCriteria validatedAcceptanceCriteria, ValidatedEstimation validatedEstimation) {
        super(pointsValuation, violations, rating, teamTask);
        this.validatedAcceptanceCriteria = validatedAcceptanceCriteria;
        this.validatedEstimation = validatedEstimation;
    }
}