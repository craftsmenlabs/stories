package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;

import java.util.ArrayList;

/**
 * Assigns points if a estimation is ok
 */
public class EstimationScorer {
    public static EstimationValidatorEntry performScorer(Float estimation, ValidationConfigCopy validationConfig) {

        float points;
        if(estimation == null || estimation.compareTo(0f) == 0){
            points = 0f;
        }else{
            points = 1f;
        }
        Rating rating = points >= validationConfig.getEstimation().getRatingtreshold()? Rating.SUCCES : Rating.FAIL;

        return EstimationValidatorEntry
                .builder()
                .estimation(estimation)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .rating(rating)
                .isActive(validationConfig.getEstimation().isActive())
                .build();
    }

}
