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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureValidatorEntry {
    @JsonProperty("feature")
    private Feature feature;

    private float pointsValuation = 0.0f;

    @JsonProperty("violations")
    private List<Violation> violations;

    @JsonProperty("rating")
    private Rating rating;

    private boolean isActive;

    @JsonProperty("userStoryValidatorEntry")
    private UserStoryValidatorEntry userStoryValidatorEntry;

    @JsonProperty("acceptanceCriteriaValidatorEntry")
    private AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry;

    @JsonProperty("estimationValidatorEntry")
    private EstimationValidatorEntry estimationValidatorEntry;
}