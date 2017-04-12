package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.Collections;

/**
 * Assigns points if a estimation is ok
 */
public class EstimationScorer extends AbstractScorer<Double, ValidatedEstimation> {
    double potentialPoints;

    public EstimationScorer(double potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
        this.potentialPoints = potentialPoints;
    }

    public ValidatedEstimation validate(Double estimation) {
        ValidatedEstimation validatedEstimation = ValidatedEstimation
                .builder()
                .item(estimation)
                .build();

        double points;
        if (estimation == null) {
            points = 0.0;
            validatedEstimation.setViolations(Collections.singletonList(new Violation(
                    ViolationType.EstimationEmptyViolation,
                    "Estimation is empty",
                    potentialPoints)));
            validatedEstimation.setRating(Rating.FAIL);
        } else {
            points = potentialPoints;
            validatedEstimation.setRating(Rating.SUCCESS);
            validatedEstimation.setViolations(Collections.emptyList());
        }

        validatedEstimation.setPoints(points, potentialPoints);
        return validatedEstimation;
    }
}
