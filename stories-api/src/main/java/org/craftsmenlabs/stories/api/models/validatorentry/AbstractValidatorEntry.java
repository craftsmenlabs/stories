package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.*;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractValidatorEntry implements ValidatorEntry{
    private float pointsValuation = 0.0f;
    private List<Violation> violations;
    private Rating rating;
    private boolean isActive;
}
