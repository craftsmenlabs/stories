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

    @JsonProperty("teamTaskValidatorEntries")
    private BacklogItemList<TeamTaskValidatorEntry> teamTaskValidatorEntries;

    public BacklogValidatorEntry() {
    }

    public BacklogValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, Backlog backlog, BacklogItemList<FeatureValidatorEntry> featureValidatorEntries, BacklogItemList<BugValidatorEntry> bugValidatorEntries, BacklogItemList<EpicValidatorEntry> epicValidatorEntries, BacklogItemList<TeamTaskValidatorEntry> teamTaskValidatorEntries) {
        super(pointsValuation, violations, rating);
        this.backlog = backlog;
        this.featureValidatorEntries = featureValidatorEntries;
        this.bugValidatorEntries = bugValidatorEntries;
        this.epicValidatorEntries = epicValidatorEntries;
        this.teamTaskValidatorEntries = teamTaskValidatorEntries;
    }

    public static BacklogValidatorEntryBuilder builder() {
        return new BacklogValidatorEntryBuilder();
    }

    @JsonIgnore
    public List<? super BacklogItem> getAllValidatorEntries(){
        return Arrays.asList(featureValidatorEntries, bugValidatorEntries, epicValidatorEntries, teamTaskValidatorEntries);
    }

    public BacklogValidatorEntry(float pointsValuation,
                                 List<Violation> violations,
                                 Rating rating,
                                 Backlog backlog,
                                 List<FeatureValidatorEntry> featureValidatorEntries,
                                 List<BugValidatorEntry> bugValidatorEntries,
                                 List<EpicValidatorEntry> epicValidatorEntries,
                                 List<TeamTaskValidatorEntry> teamTaskValidatorEntries
    ) {
        super(pointsValuation, violations, rating);
        this.backlog = backlog;

        this.featureValidatorEntries = BacklogItemList.<FeatureValidatorEntry>builder().items(featureValidatorEntries).build();
        this.bugValidatorEntries     = BacklogItemList.<BugValidatorEntry>builder().items(bugValidatorEntries).build();
        this.epicValidatorEntries    = BacklogItemList.<EpicValidatorEntry>builder().items(epicValidatorEntries).build();
        this.teamTaskValidatorEntries= BacklogItemList.<TeamTaskValidatorEntry>builder().items(teamTaskValidatorEntries).build();

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

    public BacklogItemList<TeamTaskValidatorEntry> getTeamTaskValidatorEntries() {
        return this.teamTaskValidatorEntries;
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

    public void setTeamTaskValidatorEntries(BacklogItemList<TeamTaskValidatorEntry> teamTaskValidatorEntries) {
        this.teamTaskValidatorEntries = teamTaskValidatorEntries;
    }

    public static class BacklogValidatorEntryBuilder {
        private Backlog backlog;
        private BacklogItemList<FeatureValidatorEntry> featureValidatorEntries;
        private BacklogItemList<BugValidatorEntry> bugValidatorEntries;
        private BacklogItemList<EpicValidatorEntry> epicValidatorEntries;
        private BacklogItemList<TeamTaskValidatorEntry> teamTaskValidatorEntries;
        private float pointsValuation = 0.0f;
        private List<Violation> violations;
        private Rating rating;

        BacklogValidatorEntryBuilder() {
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

        public BacklogValidatorEntry.BacklogValidatorEntryBuilder teamTaskValidatorEntries(BacklogItemList<TeamTaskValidatorEntry> teamTaskValidatorEntries) {
            this.teamTaskValidatorEntries = teamTaskValidatorEntries;
            return this;
        }

        public BacklogValidatorEntry build() {
            return new BacklogValidatorEntry(pointsValuation, violations, rating, backlog, featureValidatorEntries, bugValidatorEntries, epicValidatorEntries, teamTaskValidatorEntries);
        }

        public String toString() {
            return "org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry.BacklogValidatorEntryBuilder(backlog=" + this.backlog + ", featureValidatorEntries=" + this.featureValidatorEntries + ", bugValidatorEntries=" + this.bugValidatorEntries + ", epicValidatorEntries=" + this.epicValidatorEntries + ", teamTaskValidatorEntries=" + this.teamTaskValidatorEntries + ")";
        }
    }
}
