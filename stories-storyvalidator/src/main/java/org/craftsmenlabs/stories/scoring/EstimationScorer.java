package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;

import java.util.ArrayList;

/**
 * Assigns points if a estimation is ok
 */
public class EstimationScorer {
    public static ValidatedEstimation performScorer(Float estimation, ValidationConfig validationConfig) {

        float points;
        if(estimation == null || estimation.compareTo(0f) == 0){
            points = 0f;
        }else{
            points = 1f;
        }
        Rating rating = points >= validationConfig.getEstimation().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedEstimation
                .builder()
                .item(estimation)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .rating(rating)
                .build();
    }

}
