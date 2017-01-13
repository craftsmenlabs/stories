package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.Backlog;
import org.craftsmenlabs.stories.api.models.items.types.AbstractScorable;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Data
public class BacklogValidatorEntry extends AbstractScorable {

    @JsonProperty("backlog")
    private Backlog backlog;

    @JsonProperty("featureValidatorEntries")
    private BacklogItemList<ValidatedFeature> featureValidatorEntries = new BacklogItemList<>();

    @JsonProperty("bugValidatorEntries")
    private BacklogItemList<ValidatedBug> bugValidatorEntries = new BacklogItemList<>();

    @JsonProperty("epicValidatorEntries")
    private BacklogItemList<ValidatedEpic> epicValidatorEntries = new BacklogItemList<>();

    @JsonProperty("teamTaskValidatorEntries")
    private BacklogItemList<ValidatedTeamTask> teamTaskValidatorEntries = new BacklogItemList<>();

    @Builder
    public BacklogValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, Backlog backlog, BacklogItemList<ValidatedFeature> featureValidatorEntries, BacklogItemList<ValidatedBug> bugValidatorEntries, BacklogItemList<ValidatedEpic> epicValidatorEntries, BacklogItemList<ValidatedTeamTask> teamTaskValidatorEntries) {
        super(pointsValuation, violations, rating);
        this.backlog = backlog;
        this.featureValidatorEntries = featureValidatorEntries;
        this.bugValidatorEntries = bugValidatorEntries;
        this.epicValidatorEntries = epicValidatorEntries;
        this.teamTaskValidatorEntries = teamTaskValidatorEntries;
    }

    @JsonIgnore
    public List<? super ValidatedBacklogItem> getAllValidatorEntries() {
        return Arrays.asList(featureValidatorEntries, bugValidatorEntries, epicValidatorEntries, teamTaskValidatorEntries);
    }
}
