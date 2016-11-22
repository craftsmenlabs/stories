package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Bug;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BugValidatorEntry extends AbstractValidatorEntry {
    @JsonProperty("bug")
    private Bug bug;

    @JsonProperty("violations")
    private List<Violation> violations;

    @JsonProperty("rating")
    private Rating rating;


    @JsonProperty("averageScore")
    private float pointsValuation = 0.0f;

    private boolean isActive;

    @Override
    public String getRank() {
        return bug.getRank();
    }
}
