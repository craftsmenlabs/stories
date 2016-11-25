package org.craftsmenlabs.stories.api.models.validatorentry;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

public class EstimationValidatorEntry extends AbstractStoryalidatorEntryItem<Float> {

    public EstimationValidatorEntry() {
    }

    public EstimationValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, Float item) {
        super(pointsValuation, violations, rating, item);
    }

    public static EstimationValidatorEntryBuilder builder() {
        return new EstimationValidatorEntryBuilder();
    }


    public static class EstimationValidatorEntryBuilder {
        private float pointsValuation;
        private List<Violation> violations;
        private Rating rating;
        private Float item;

        EstimationValidatorEntryBuilder() {
        }

        public EstimationValidatorEntry.EstimationValidatorEntryBuilder pointsValuation(float pointsValuation) {
            this.pointsValuation = pointsValuation;
            return this;
        }

        public EstimationValidatorEntry.EstimationValidatorEntryBuilder violations(List<Violation> violations) {
            this.violations = violations;
            return this;
        }

        public EstimationValidatorEntry.EstimationValidatorEntryBuilder rating(Rating rating) {
            this.rating = rating;
            return this;
        }

        public EstimationValidatorEntry.EstimationValidatorEntryBuilder item(Float item) {
            this.item = item;
            return this;
        }

        public EstimationValidatorEntry build() {
            return new EstimationValidatorEntry(pointsValuation, violations, rating, item);
        }

        public String toString() {
            return "org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry.EstimationValidatorEntryBuilder(pointsValuation=" + this.pointsValuation + ", violations=" + this.violations + ", rating=" + this.rating + ", item=" + this.item + ")";
        }
    }
}