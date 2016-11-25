package org.craftsmenlabs.stories.api.models.validatorentry;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;


//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public abstract class AbstractValidatorEntry implements ValidatorEntry {
    private float pointsValuation = 0.0f;

    @JsonProperty("violations")
    private List<Violation> violations;

    @JsonProperty("rating")
    private Rating rating;

    private boolean isActive;

    public abstract String getRank();

    @JsonGetter
    public abstract ValidatorEntryType getType();
}
