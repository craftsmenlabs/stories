package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.TeamTask;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamTaskValidatorEntry extends BacklogItem {
    @JsonProperty("team-task")
    private TeamTask teamTask;

    @JsonProperty("acceptanceCriteriaValidatorEntry")
    private AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry;

    @JsonProperty("estimationValidatorEntry")
    private EstimationValidatorEntry estimationValidatorEntry;

    @Builder
    public TeamTaskValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, TeamTask teamTask, AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry, EstimationValidatorEntry estimationValidatorEntry) {
        super(pointsValuation, violations, rating);
        this.teamTask = teamTask;
        this.acceptanceCriteriaValidatorEntry = acceptanceCriteriaValidatorEntry;
        this.estimationValidatorEntry = estimationValidatorEntry;
    }

    @Override
    public String getRank() {
        return teamTask.getRank();
    }

    @Override
    public ValidatorEntryType getType() {
        return ValidatorEntryType.TEAMTASK;
    }
}