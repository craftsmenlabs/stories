package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureValidatorEntry extends AbstractValidatorEntry {
    @JsonProperty("feature")
    private Feature feature;

    @JsonProperty("userStoryValidatorEntry")
    private UserStoryValidatorEntry userStoryValidatorEntry;

    @JsonProperty("acceptanceCriteriaValidatorEntry")
    private AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry;

    @JsonProperty("estimationValidatorEntry")
    private EstimationValidatorEntry estimationValidatorEntry;

    @Builder
    public FeatureValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, boolean isActive, UserStoryValidatorEntry userStoryValidatorEntry, Feature feature, AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry, EstimationValidatorEntry estimationValidatorEntry) {
        super(pointsValuation, violations, rating, isActive);

        this.feature = feature;
        this.userStoryValidatorEntry = userStoryValidatorEntry;
        this.acceptanceCriteriaValidatorEntry = acceptanceCriteriaValidatorEntry;
        this.estimationValidatorEntry = estimationValidatorEntry;

    }

    @Override
    public String getRank() {
        return feature.getRank();
    }

    @Override
    public String getType() {
        return "feature";
    }
}