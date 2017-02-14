package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Assigns points if a estimation is ok
 */
public class EstimationScorer {
    public static ValidatedEstimation performScorer(Float estimation, ValidationConfig validationConfig) {
        List<Violation> violations = new ArrayList<>();

        float points;
        if (estimation == null) {
            points = 0f;
            violations.add(new Violation(
                    ViolationType.FieldEmptyViolation,
                    "Estimation field is empty!",
                    1f));
        } else {
            points = 1f;
        }
        Rating rating = points >= validationConfig.getEstimation().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedEstimation
                .builder()
                .item(estimation)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .build();
    }

}
