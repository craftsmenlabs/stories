package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.Feature;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedFeature extends ValidatedBacklogItem<Feature> {
    @JsonProperty("userStoryValidatorEntry")
    private ValidatedUserStory validatedUserStory;

    @JsonProperty("acceptanceCriteriaValidatorEntry")
    private ValidatedAcceptanceCriteria validatedAcceptanceCriteria;

    @JsonProperty("estimationValidatorEntry")
    private ValidatedEstimation validatedEstimation;

    @Builder
    public ValidatedFeature(float pointsValuation, List<Violation> violations, Rating rating, ValidatedUserStory validatedUserStory, Feature feature, ValidatedAcceptanceCriteria validatedAcceptanceCriteria, ValidatedEstimation validatedEstimation) {
        super(pointsValuation, violations, rating, feature);
        this.validatedUserStory = validatedUserStory;
        this.validatedAcceptanceCriteria = validatedAcceptanceCriteria;
        this.validatedEstimation = validatedEstimation;

    }
}