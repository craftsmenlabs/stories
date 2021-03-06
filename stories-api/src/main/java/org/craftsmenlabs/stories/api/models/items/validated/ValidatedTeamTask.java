package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.TeamTask;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("TEAM_TASK")
public class ValidatedTeamTask extends ValidatedBacklogItem<TeamTask> {
    @JsonProperty("acceptanceCriteriaValidatorEntry")
    private ValidatedAcceptanceCriteria validatedAcceptanceCriteria;

    @JsonProperty("estimationValidatorEntry")
    private ValidatedEstimation validatedEstimation;

    @Builder
    public ValidatedTeamTask(List<Violation> violations, Rating rating, TeamTask teamTask, ValidatedAcceptanceCriteria validatedAcceptanceCriteria, ValidatedEstimation validatedEstimation, double scoredPercentage, double missedPercentage, double scoredPoints, double missedPoints) {
        super(violations, rating, teamTask, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
        this.validatedAcceptanceCriteria = validatedAcceptanceCriteria;
        this.validatedEstimation = validatedEstimation;
    }

    @Override
    public List<AbstractValidatedItem<?>> getSubItems() {
        return Arrays.asList(validatedAcceptanceCriteria, validatedEstimation);
    }
}