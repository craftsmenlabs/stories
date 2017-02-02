package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonTypeName("FEATURE")
public class ValidatedFeature extends ValidatedBacklogItem<Feature> {
    @JsonProperty("userStoryValidatorEntry")
    private ValidatedUserStory validatedUserStory;

    @JsonProperty("acceptanceCriteriaValidatorEntry")
    private ValidatedAcceptanceCriteria validatedAcceptanceCriteria;

    @JsonProperty("estimationValidatorEntry")
    private ValidatedEstimation validatedEstimation;

    @Builder
    public ValidatedFeature(List<Violation> violations, Rating rating, ValidatedUserStory validatedUserStory, Feature feature, ValidatedAcceptanceCriteria validatedAcceptanceCriteria, ValidatedEstimation validatedEstimation, float scoredPercentage, float missedPercentage, float scoredPoints, float missedPoints) {
        super(violations, rating, feature, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
        this.validatedUserStory = validatedUserStory;
        this.validatedAcceptanceCriteria = validatedAcceptanceCriteria;
        this.validatedEstimation = validatedEstimation;
    }
}