package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Assigns points to a feature, based on all
 * underlying fields, such as user story, acceptance criteria, estimated points
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

        ValidatedUserStory validatedUserStory = StoryScorer.performScorer(feature.getUserstory(), validationConfig);
        ValidatedAcceptanceCriteria validatedAcceptanceCriteria = AcceptanceCriteriaScorer.performScorer(feature.getAcceptanceCriteria(), validationConfig);
        ValidatedEstimation validatedEstimation = EstimationScorer.performScorer(feature.getEstimation(), validationConfig);


        List<Map.Entry<Boolean, AbstractValidatedItem>> entryList = new ArrayList<>();
        entryList.add(new AbstractMap.SimpleEntry<>(validationConfig.getStory().isActive(), validatedUserStory));
        entryList.add(new AbstractMap.SimpleEntry<>(validationConfig.getCriteria().isActive(), validatedAcceptanceCriteria));
        entryList.add(new AbstractMap.SimpleEntry<>(validationConfig.getEstimation().isActive(), validatedEstimation));


        float points = (float)
                entryList.stream()
                        .filter(Map.Entry::getKey)
                        .mapToDouble(item -> item.getValue().getPointsValuation())
                        .average()
                        .orElse(0.0);

        Rating rating = points >= validationConfig.getFeature().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedFeature
                .builder()
                .feature(feature)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .rating(rating)
                .validatedUserStory(validatedUserStory)
                .validatedAcceptanceCriteria(validatedAcceptanceCriteria)
                .validatedEstimation(validatedEstimation)
                .build();
    }
}
