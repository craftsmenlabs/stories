package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Assigns points if a estimation is ok
 */
public class EstimationScorer {
    public static EstimationValidatorEntry performScorer(Float estimation, ValidationConfig validationConfig) {
        List<Violation> violations = new ArrayList<>();

        float points;
        if(estimation == null || estimation.compareTo(0f) == 0){
            points = 0f;
            violations.add(new Violation(
                    ViolationType.EstimationEmptyViolation,
                    "Estimation is empty or zero",
                    1f));
        }else{
            points = 1f;
        }
        Rating rating = points >= validationConfig.getEstimation().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return EstimationValidatorEntry
                .builder()
                .item(estimation)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .build();
    }

}
