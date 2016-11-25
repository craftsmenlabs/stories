package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Bug;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

public class BugValidatorEntry extends BacklogItem {
    @JsonProperty("bug")
    private Bug bug;

    public BugValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, Bug bug) {
        super(pointsValuation, violations, rating);
        this.bug = bug;
    }

    @java.beans.ConstructorProperties({"bug"})
    public BugValidatorEntry(Bug bug) {
        this.bug = bug;
    }

    public BugValidatorEntry() {
    }

    public static BugValidatorEntryBuilder builder() {
        return new BugValidatorEntryBuilder();
    }

    @Override
    public String getRank() {
        return bug.getRank();
    }

    @Override
    public ValidatorEntryType getType() {
        return ValidatorEntryType.BUG;
    }


    public Bug getBug() {
        return this.bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }



    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BugValidatorEntry)) return false;
        final BugValidatorEntry other = (BugValidatorEntry) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$bug = this.getBug();
        final Object other$bug = other.getBug();
        if (this$bug == null ? other$bug != null : !this$bug.equals(other$bug)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $bug = this.getBug();
        result = result * PRIME + ($bug == null ? 43 : $bug.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof BugValidatorEntry;
    }

    public String toString() {
        return "org.craftsmenlabs.stories.api.models.validatorentry.BugValidatorEntry(bug=" + this.getBug() + ")";
    }

    public static class BugValidatorEntryBuilder {
        private float pointsValuation;
        private List<Violation> violations;
        private Rating rating;
        private Bug bug;

        BugValidatorEntryBuilder() {
        }

        public BugValidatorEntry.BugValidatorEntryBuilder pointsValuation(float pointsValuation) {
            this.pointsValuation = pointsValuation;
            return this;
        }

        public BugValidatorEntry.BugValidatorEntryBuilder violations(List<Violation> violations) {
            this.violations = violations;
            return this;
        }

        public BugValidatorEntry.BugValidatorEntryBuilder rating(Rating rating) {
            this.rating = rating;
            return this;
        }

        public BugValidatorEntry.BugValidatorEntryBuilder bug(Bug bug) {
            this.bug = bug;
            return this;
        }

        public BugValidatorEntry build() {
            return new BugValidatorEntry(pointsValuation, violations, rating, bug);
        }

        public String toString() {
            return "org.craftsmenlabs.stories.api.models.validatorentry.BugValidatorEntry.BugValidatorEntryBuilder(pointsValuation=" + this.pointsValuation + ", violations=" + this.violations + ", rating=" + this.rating + ", bug=" + this.bug + ")";
        }
    }
}
