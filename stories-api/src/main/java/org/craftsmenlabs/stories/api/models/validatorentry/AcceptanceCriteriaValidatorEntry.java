package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;


@Data
public class AcceptanceCriteriaValidatorEntry extends AbstractStoryalidatorEntryItem<String> {

    public AcceptanceCriteriaValidatorEntry() {
    }

    public AcceptanceCriteriaValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, String item) {
        super(pointsValuation, violations, rating, item);
    }

    public static AcceptanceCriteriaValidatorEntryBuilder builder() {
        return new AcceptanceCriteriaValidatorEntryBuilder();
    }

    public static class AcceptanceCriteriaValidatorEntryBuilder {
        private float pointsValuation;
        private List<Violation> violations;
        private Rating rating;
        private String item;

        AcceptanceCriteriaValidatorEntryBuilder() {
        }

        public AcceptanceCriteriaValidatorEntry.AcceptanceCriteriaValidatorEntryBuilder pointsValuation(float pointsValuation) {
            this.pointsValuation = pointsValuation;
            return this;
        }

        public AcceptanceCriteriaValidatorEntry.AcceptanceCriteriaValidatorEntryBuilder violations(List<Violation> violations) {
            this.violations = violations;
            return this;
        }

        public AcceptanceCriteriaValidatorEntry.AcceptanceCriteriaValidatorEntryBuilder rating(Rating rating) {
            this.rating = rating;
            return this;
        }

        public AcceptanceCriteriaValidatorEntry.AcceptanceCriteriaValidatorEntryBuilder item(String item) {
            this.item = item;
            return this;
        }

        public AcceptanceCriteriaValidatorEntry build() {
            return new AcceptanceCriteriaValidatorEntry(pointsValuation, violations, rating, item);
        }

        public String toString() {
            return "org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry.AcceptanceCriteriaValidatorEntryBuilder(pointsValuation=" + this.pointsValuation + ", violations=" + this.violations + ", rating=" + this.rating + ", item=" + this.item + ")";
        }
    }
}