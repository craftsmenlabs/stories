package org.craftsmenlabs.stories.scoring;

import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Assigns scoredPercentage to a feature, based on all
 * underlying fields, such as user story, acceptance criteria, estimated scoredPercentage
 */
public class FeatureScorer extends AbstractScorer<Feature, ValidatedFeature> {

    public FeatureScorer(ValidationConfig validationConfig) {
        super(validationConfig);
    }

    @Override
    public ValidatedFeature validate(Feature feature) {
        if (feature == null) {
            feature = Feature.builder().rank("0").summary("").userstory("").acceptanceCriteria("").key("0").build();
        }
        if (feature.getUserstory() == null) {
            feature.setUserstory("");
        }
        if (feature.getAcceptanceCriteria() == null) {
            feature.setAcceptanceCriteria("");
        }
        if (feature.getEstimation() == null) {
            feature.setEstimation(null);
        }

        final float potentialPointsPerSubItem = feature.getPotentialPoints() / Stream.of(validationConfig.getStory().isActive(), validationConfig.getCriteria().isActive(), validationConfig.getEstimation().isActive()).filter(item -> item).count();

        final ValidatedFeature validatedFeature = ValidatedFeature
                .builder()
                .feature(feature)
                .violations(new ArrayList<>())
                .build();

        final Stream<Boolean> actives = Stream.of(validationConfig.getStory().isActive(), validationConfig.getCriteria().isActive(), validationConfig.getEstimation().isActive());
        final Stream<? extends Serializable> datas = Stream.of(feature.getUserstory(), feature.getAcceptanceCriteria(), feature.getEstimation());
        final Stream<Class<?>> scorers = Stream.of(StoryScorer.class, AcceptanceCriteriaScorer.class, EstimationScorer.class);

        StreamUtils.zip(actives, datas, scorers, Triple::of)
                .filter(Triple::getLeft)
        .;

        float points = 0f;
        if(validationConfig.getStory().isActive()){
            ValidatedUserStory validatedUserStory = StoryScorer.validate(feature.getUserstory(), potentialPointsPerSubItem, validationConfig);
            validatedFeature.setValidatedUserStory(validatedUserStory);
            points += validatedUserStory.getScoredPoints();
        }
        if(validationConfig.getCriteria().isActive()){
            ValidatedAcceptanceCriteria validatedAcceptanceCriteria = AcceptanceCriteriaScorer.performScorer(feature.getAcceptanceCriteria(), potentialPointsPerSubItem, validationConfig);
            validatedFeature.setValidatedAcceptanceCriteria(validatedAcceptanceCriteria);
            points += validatedAcceptanceCriteria.getScoredPoints();
        }
        if(validationConfig.getEstimation().isActive()){
            ValidatedEstimation validatedEstimation = EstimationScorer.performScorer(feature.getEstimation(), potentialPointsPerSubItem, validationConfig);
            validatedFeature.setValidatedEstimation(validatedEstimation);
            points += validatedEstimation.getScoredPoints();
        }

        Rating rating = points >= validationConfig.getFeature().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        validatedFeature.setRating(rating);
        validatedFeature.setPoints(points, validatedFeature.getItem().getPotentialPoints());

        return validatedFeature;
    }
}
