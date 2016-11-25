package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
public class UserStoryValidatorEntry extends AbstractStoryalidatorEntryItem<String> {

    public UserStoryValidatorEntry() {
    }

    public UserStoryValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, String item) {
        super(pointsValuation, violations, rating, item);
    }

    public static UserStoryValidatorEntryBuilder builder() {
        return new UserStoryValidatorEntryBuilder();
    }

    public static class UserStoryValidatorEntryBuilder {
        private float pointsValuation;
        private List<Violation> violations;
        private Rating rating;
        private String item;

        UserStoryValidatorEntryBuilder() {
        }

        public UserStoryValidatorEntry.UserStoryValidatorEntryBuilder pointsValuation(float pointsValuation) {
            this.pointsValuation = pointsValuation;
            return this;
        }

        public UserStoryValidatorEntry.UserStoryValidatorEntryBuilder violations(List<Violation> violations) {
            this.violations = violations;
            return this;
        }

        public UserStoryValidatorEntry.UserStoryValidatorEntryBuilder rating(Rating rating) {
            this.rating = rating;
            return this;
        }

        public UserStoryValidatorEntry.UserStoryValidatorEntryBuilder item(String item) {
            this.item = item;
            return this;
        }

        public UserStoryValidatorEntry build() {
            return new UserStoryValidatorEntry(pointsValuation, violations, rating, item);
        }

        public String toString() {
            return "org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry.UserStoryValidatorEntryBuilder(pointsValuation=" + this.pointsValuation + ", violations=" + this.violations + ", rating=" + this.rating + ", item=" + this.item + ")";
        }
    }
}