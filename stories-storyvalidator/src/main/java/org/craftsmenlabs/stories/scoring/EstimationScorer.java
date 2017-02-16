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
public class EstimationScorer extends AbstractScorer<Double, ValidatedEstimation> {
    double potentialPoints;

    public EstimationScorer(double potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
        this.potentialPoints = potentialPoints;
    }

    public ValidatedEstimation validate(Double estimation) {
        List<Violation> violations = new ArrayList<>();

        double points;
        if (estimation == null) {
            points = 0.0;
            violations.add(new Violation(
                    ViolationType.EstimationEmptyViolation,
                    "Estimation is empty",
                    potentialPoints));
        } else {
            points = potentialPoints;
        }

        ValidatedEstimation validatedEstimation = ValidatedEstimation
                .builder()
                .item(estimation)
                .violations(violations)
                .build();
        validatedEstimation.setPoints(points, potentialPoints);
        Rating rating = validatedEstimation.getScoredPercentage() >= validationConfig.getEstimation().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;
        validatedEstimation.setRating(rating);
        return validatedEstimation;
    }
}
