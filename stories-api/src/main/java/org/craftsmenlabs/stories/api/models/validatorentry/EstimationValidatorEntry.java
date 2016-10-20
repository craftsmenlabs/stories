package org.craftsmenlabs.stories.api.models.validatorentry;

import java.util.List;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class EstimationValidatorEntry {
    private Float estimation;
    private float pointsValuation = 0.0f;
    private List<Violation> violations;
    private Rating rating;
}