package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Assigns scoredPercentage to a feature, based on all
 * underlying fields, such as user story, acceptance criteria, estimated scoredPercentage
 */
public class FeatureScorer extends AbstractScorer<Feature, ValidatedFeature> {

    public FeatureScorer(ValidationConfig validationConfig) {
        super(1.0, validationConfig);
    }

    public FeatureScorer(double potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
    }

    @Override
    public ValidatedFeature validate(Feature feature) {
        if(feature == null){
            feature = Feature.empty();
        }

        final long count = Stream.of(validationConfig.getStory().isActive(), validationConfig.getCriteria().isActive(), validationConfig.getEstimation().isActive()).filter(item -> item).count();
        final double potentialPointsPerSubItem = count == 0 ? 0 : potentialPoints / count;

        final ValidatedFeature validatedFeature = ValidatedFeature
                .builder()
                .feature(feature)
                .violations(new ArrayList<>())
                .build();

        double points = 0.0;
        if(validationConfig.getStory().isActive()){
            ValidatedUserStory validatedUserStory = new StoryScorer(potentialPointsPerSubItem, validationConfig).validate(feature.getUserstory());
            validatedFeature.setValidatedUserStory(validatedUserStory);
            points += validatedUserStory.getScoredPoints();
        }
        if(validationConfig.getCriteria().isActive()){
            ValidatedAcceptanceCriteria validatedAcceptanceCriteria = new AcceptanceCriteriaScorer(potentialPointsPerSubItem, validationConfig).validate(feature.getAcceptanceCriteria());
            validatedFeature.setValidatedAcceptanceCriteria(validatedAcceptanceCriteria);
            points += validatedAcceptanceCriteria.getScoredPoints();
        }
        if(validationConfig.getEstimation().isActive()){
            ValidatedEstimation validatedEstimation = new EstimationScorer(potentialPointsPerSubItem, validationConfig).validate(feature.getEstimation());
            validatedFeature.setValidatedEstimation(validatedEstimation);
            points += validatedEstimation.getScoredPoints();
        }

        Rating rating = points >= validationConfig.getFeature().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        validatedFeature.setRating(rating);
        validatedFeature.setPoints(points, potentialPoints);

        return validatedFeature;
    }
}
