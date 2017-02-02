package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedUserStory;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.*;
import java.util.stream.Collectors;
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

        ValidatedUserStory validatedUserStory = StoryScorer.performScorer(feature.getUserstory(), potentialPointsPerSubItem, validationConfig);
        ValidatedAcceptanceCriteria validatedAcceptanceCriteria = AcceptanceCriteriaScorer.performScorer(feature.getAcceptanceCriteria(), potentialPointsPerSubItem, validationConfig);
        ValidatedEstimation validatedEstimation = EstimationScorer.performScorer(feature.getEstimation(), potentialPointsPerSubItem, validationConfig);

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

        List<Violation> violations = entryList.stream()
                .filter(entry -> entry.getKey() && entry.getValue().getViolations() != null)
                .map(entry -> entry.getValue().getViolations())
                .flatMap(Collection<Violation>::stream)
                .collect(Collectors.toList());

        Rating rating = points >= validationConfig.getFeature().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedFeature
                .builder()
                .feature(feature)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .validatedUserStory(validatedUserStory)
                .validatedAcceptanceCriteria(validatedAcceptanceCriteria)
                .validatedEstimation(validatedEstimation)
                .build();
    }
}
