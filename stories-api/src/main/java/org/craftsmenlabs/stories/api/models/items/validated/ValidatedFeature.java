package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Arrays;
import java.util.Collections;
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
    public ValidatedFeature(List<Violation> violations, Rating rating, ValidatedUserStory validatedUserStory, Feature feature, ValidatedAcceptanceCriteria validatedAcceptanceCriteria, ValidatedEstimation validatedEstimation, double scoredPercentage, double missedPercentage, double scoredPoints, double missedPoints) {
        super(violations, rating, feature, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
        this.validatedUserStory = validatedUserStory;
        this.validatedAcceptanceCriteria = validatedAcceptanceCriteria;
        this.validatedEstimation = validatedEstimation;
    }

    public ValidatedUserStory getValidatedUserStory() {
        if(validatedUserStory == null){
            return ValidatedUserStory.empty();
        }
        return validatedUserStory;
    }

    public void setValidatedUserStory(ValidatedUserStory validatedUserStory) {
        this.validatedUserStory = validatedUserStory;
    }

    public ValidatedAcceptanceCriteria getValidatedAcceptanceCriteria() {
        if(validatedAcceptanceCriteria == null){
            return ValidatedAcceptanceCriteria.empty();
        }

        return validatedAcceptanceCriteria;
    }

    public void setValidatedAcceptanceCriteria(ValidatedAcceptanceCriteria validatedAcceptanceCriteria) {
        this.validatedAcceptanceCriteria = validatedAcceptanceCriteria;
    }

    public ValidatedEstimation getValidatedEstimation() {
        if(validatedEstimation == null){
            return ValidatedEstimation.empty();
        }
        return validatedEstimation;
    }

    public void setValidatedEstimation(ValidatedEstimation validatedEstimation) {
        this.validatedEstimation = validatedEstimation;
    }

    @Override
    public List<AbstractValidatedItem<?>> getSubItems() {
        return Arrays.asList(validatedUserStory, validatedAcceptanceCriteria, validatedEstimation);
    }

    public ValidatedFeature empty(){
        return new ValidatedFeature(Collections.emptyList(), Rating.FAIL, ValidatedUserStory.empty(), Feature.empty(), ValidatedAcceptanceCriteria.empty(), ValidatedEstimation.empty(), 0, 0, 0, 0);
    }
}