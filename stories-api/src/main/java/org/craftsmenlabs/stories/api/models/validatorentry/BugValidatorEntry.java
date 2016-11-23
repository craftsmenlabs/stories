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
@NoArgsConstructor
@AllArgsConstructor
public class BugValidatorEntry extends AbstractValidatorEntry {
    @JsonProperty("bug")
    private Bug bug;

    @Builder
    public BugValidatorEntry(float pointsValuation, List<Violation> violations, Rating rating, boolean isActive, Bug bug) {
        super(pointsValuation, violations, rating, isActive);
        this.bug = bug;
    }

    @Override
    public String getRank() {
        return bug.getRank();
    }

    @Override
    public String getType() {
        return "bug";
    }
}
