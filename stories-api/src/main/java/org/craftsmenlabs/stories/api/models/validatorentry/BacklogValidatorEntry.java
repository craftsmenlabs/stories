package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class BacklogValidatorEntry extends AbstractValidatorEntry {

    @JsonProperty("backlog")
    private Backlog backlog;

    @JsonProperty("featureValidatorEntries")
    private List<FeatureValidatorEntry> featureValidatorEntries;

    @JsonProperty("bugValidatorEntries")
    private List<BugValidatorEntry> bugValidatorEntries;

    @JsonProperty("epicValidatorEntries")
    private List<EpicValidatorEntry> epicValidatorEntries;

    @JsonIgnore
    public List<? super AbstractValidatorEntry> getAllValidatorEntries(){
        return Arrays.asList(featureValidatorEntries, bugValidatorEntries, epicValidatorEntries);
    }

    private float averageScore = 0.0f;
    private float featureScore = 0.0f;
    private float bugScore = 0.0f;
    private float epicScore = 0.0f;

    @Builder
    public BacklogValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, boolean isActive,
                                 Backlog backlog, List<FeatureValidatorEntry> featureValidatorEntries, List<BugValidatorEntry> bugValidatorEntries,
                                 List<EpicValidatorEntry> epicValidatorEntries, float averageScore, float featureScore, float bugScore, float epicScore) {
        super(pointsValuation, violations, rating, isActive);
        this.backlog = backlog;
        this.featureValidatorEntries = featureValidatorEntries;
        this.bugValidatorEntries = bugValidatorEntries;
        this.epicValidatorEntries = epicValidatorEntries;

        this.averageScore = averageScore;
        this.featureScore = featureScore;
        this.bugScore = bugScore;
        this.epicScore = epicScore;
    }

    @Override
    public String getRank() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
