package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Arrays;
import java.util.List;

public class BacklogValidatorEntry extends AbstractScorable {

    @JsonProperty("backlog")
    private Backlog backlog;

    @JsonProperty("featureValidatorEntries")
    private BacklogItemList<FeatureValidatorEntry> featureValidatorEntries;

    @JsonProperty("bugValidatorEntries")
    private BacklogItemList<BugValidatorEntry> bugValidatorEntries;

    @JsonProperty("epicValidatorEntries")
    private BacklogItemList<EpicValidatorEntry> epicValidatorEntries;

    public BacklogValidatorEntry() {
    }

    public BacklogValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, Backlog backlog, BacklogItemList<FeatureValidatorEntry> featureValidatorEntries, BacklogItemList<BugValidatorEntry> bugValidatorEntries, BacklogItemList<EpicValidatorEntry> epicValidatorEntries) {
        super(pointsValuation, violations, rating);
        this.backlog = backlog;
        this.featureValidatorEntries = featureValidatorEntries;
        this.bugValidatorEntries = bugValidatorEntries;
        this.epicValidatorEntries = epicValidatorEntries;
    }

    public static BacklogValidatorEntryBuilder builder() {
        return new BacklogValidatorEntryBuilder();
    }

    @JsonIgnore
    public List<? super BacklogItem> getAllValidatorEntries(){
        return Arrays.asList(featureValidatorEntries, bugValidatorEntries, epicValidatorEntries);
    }

    public BacklogValidatorEntry(float pointsValuation,
                                 List<Violation> violations,
                                 Rating rating,
                                 Backlog backlog,
                                 List<FeatureValidatorEntry> featureValidatorEntries,
                                 List<BugValidatorEntry> bugValidatorEntries,
                                 List<EpicValidatorEntry> epicValidatorEntries
    ) {
        super(pointsValuation, violations, rating);
        this.backlog = backlog;

        this.featureValidatorEntries = BacklogItemList.<FeatureValidatorEntry>builder().items(featureValidatorEntries).build();
        this.bugValidatorEntries     = BacklogItemList.<BugValidatorEntry>builder().items(bugValidatorEntries).build();
        this.epicValidatorEntries    = BacklogItemList.<EpicValidatorEntry>builder().items(epicValidatorEntries).build();
    }

    public Backlog getBacklog() {
        return this.backlog;
    }

    public BacklogItemList<FeatureValidatorEntry> getFeatureValidatorEntries() {
        return this.featureValidatorEntries;
    }

    public BacklogItemList<BugValidatorEntry> getBugValidatorEntries() {
        return this.bugValidatorEntries;
    }

    public BacklogItemList<EpicValidatorEntry> getEpicValidatorEntries() {
        return this.epicValidatorEntries;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public void setFeatureValidatorEntries(BacklogItemList<FeatureValidatorEntry> featureValidatorEntries) {
        this.featureValidatorEntries = featureValidatorEntries;
    }

    public void setBugValidatorEntries(BacklogItemList<BugValidatorEntry> bugValidatorEntries) {
        this.bugValidatorEntries = bugValidatorEntries;
    }

    public void setEpicValidatorEntries(BacklogItemList<EpicValidatorEntry> epicValidatorEntries) {
        this.epicValidatorEntries = epicValidatorEntries;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BacklogValidatorEntry)) return false;
        final BacklogValidatorEntry other = (BacklogValidatorEntry) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$backlog = this.getBacklog();
        final Object other$backlog = other.getBacklog();
        if (this$backlog == null ? other$backlog != null : !this$backlog.equals(other$backlog)) return false;
        final Object this$featureValidatorEntries = this.getFeatureValidatorEntries();
        final Object other$featureValidatorEntries = other.getFeatureValidatorEntries();
        if (this$featureValidatorEntries == null ? other$featureValidatorEntries != null : !this$featureValidatorEntries.equals(other$featureValidatorEntries))
            return false;
        final Object this$bugValidatorEntries = this.getBugValidatorEntries();
        final Object other$bugValidatorEntries = other.getBugValidatorEntries();
        if (this$bugValidatorEntries == null ? other$bugValidatorEntries != null : !this$bugValidatorEntries.equals(other$bugValidatorEntries))
            return false;
        final Object this$epicValidatorEntries = this.getEpicValidatorEntries();
        final Object other$epicValidatorEntries = other.getEpicValidatorEntries();
        if (this$epicValidatorEntries == null ? other$epicValidatorEntries != null : !this$epicValidatorEntries.equals(other$epicValidatorEntries))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $backlog = this.getBacklog();
        result = result * PRIME + ($backlog == null ? 43 : $backlog.hashCode());
        final Object $featureValidatorEntries = this.getFeatureValidatorEntries();
        result = result * PRIME + ($featureValidatorEntries == null ? 43 : $featureValidatorEntries.hashCode());
        final Object $bugValidatorEntries = this.getBugValidatorEntries();
        result = result * PRIME + ($bugValidatorEntries == null ? 43 : $bugValidatorEntries.hashCode());
        final Object $epicValidatorEntries = this.getEpicValidatorEntries();
        result = result * PRIME + ($epicValidatorEntries == null ? 43 : $epicValidatorEntries.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof BacklogValidatorEntry;
    }

    public String toString() {
        return "org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry(backlog=" + this.getBacklog() + ", featureValidatorEntries=" + this.getFeatureValidatorEntries() + ", bugValidatorEntries=" + this.getBugValidatorEntries() + ", epicValidatorEntries=" + this.getEpicValidatorEntries() + ")";
    }

    public static class BacklogValidatorEntryBuilder {
        private Backlog backlog;
        private BacklogItemList<FeatureValidatorEntry> featureValidatorEntries;
        private BacklogItemList<BugValidatorEntry> bugValidatorEntries;
        private BacklogItemList<EpicValidatorEntry> epicValidatorEntries;
        private float pointsValuation = 0.0f;
        private List<Violation> violations;
        private Rating rating;

        BacklogValidatorEntryBuilder() {
        }

        public BacklogValidatorEntry.BacklogValidatorEntryBuilder backlog(Backlog backlog) {
            this.backlog = backlog;
            return this;
        }

        public BacklogValidatorEntry.BacklogValidatorEntryBuilder featureValidatorEntries(BacklogItemList<FeatureValidatorEntry> featureValidatorEntries) {
            this.featureValidatorEntries = featureValidatorEntries;
            return this;
        }

        public BacklogValidatorEntry.BacklogValidatorEntryBuilder bugValidatorEntries(BacklogItemList<BugValidatorEntry> bugValidatorEntries) {
            this.bugValidatorEntries = bugValidatorEntries;
            return this;
        }

        public BacklogValidatorEntry.BacklogValidatorEntryBuilder epicValidatorEntries(BacklogItemList<EpicValidatorEntry> epicValidatorEntries) {
            this.epicValidatorEntries = epicValidatorEntries;
            return this;
        }


        public BacklogValidatorEntry.BacklogValidatorEntryBuilder pointsValuation(float pointsValuation) {
            this.pointsValuation = pointsValuation;
            return this;
        }


        public BacklogValidatorEntry.BacklogValidatorEntryBuilder violations(List<Violation> violations) {
            this.violations = violations;
            return this;
        }


        public BacklogValidatorEntry.BacklogValidatorEntryBuilder rating(Rating rating) {
            this.rating = rating;
            return this;
        }


        public BacklogValidatorEntry build() {
            return new BacklogValidatorEntry(pointsValuation, violations, rating, backlog, featureValidatorEntries, bugValidatorEntries, epicValidatorEntries);
        }

        public String toString() {
            return "org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry.BacklogValidatorEntryBuilder(backlog=" + this.backlog + ", featureValidatorEntries=" + this.featureValidatorEntries + ", bugValidatorEntries=" + this.bugValidatorEntries + ", epicValidatorEntries=" + this.epicValidatorEntries + ")";
        }
    }
}
