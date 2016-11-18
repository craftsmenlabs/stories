package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BacklogValidatorEntry {
    @JsonProperty("backlog")
    @JsonIgnore
    private Backlog backlog;

    @JsonProperty("featureValidatorEntries")
    @JsonIgnore
    private List<FeatureValidatorEntry> featureValidatorEntries;
    @JsonIgnore
    @JsonProperty("pointsValuation")
    private float pointsValuation = 0.0f;
    @JsonIgnore
    @JsonProperty("violations")
    private List<Violation> violations;
    @JsonIgnore
    @JsonProperty("rating")
    private Rating rating;
}
