package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.validatorentry.*;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Assigns points to a feature, based on all
 * underlying fields, such as user story, acceptance criteria, estimated points
 */
public class FeatureScorer {

    public static FeatureValidatorEntry performScorer(Feature feature, ValidationConfig validationConfig) {
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

        UserStoryValidatorEntry userStoryValidatorEntry = StoryScorer.performScorer(feature.getUserstory(), validationConfig);
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(feature.getAcceptanceCriteria(), validationConfig);
        EstimationValidatorEntry estimationValidatorEntry = EstimationScorer.performScorer(feature.getEstimation(), validationConfig);


        List<Map.Entry<Boolean, AbstractStoryalidatorEntryItem>> entryList = new ArrayList<>();
        entryList.add(new AbstractMap.SimpleEntry<>(validationConfig.getStory().isActive(),  userStoryValidatorEntry));
        entryList.add(new AbstractMap.SimpleEntry<>(validationConfig.getCriteria().isActive(),  acceptanceCriteriaValidatorEntry));
        entryList.add(new AbstractMap.SimpleEntry<>(validationConfig.getEstimation().isActive(),  estimationValidatorEntry));


        float points = (float)
                entryList.stream()
                        .filter(Map.Entry::getKey)
                        .mapToDouble(item -> item.getValue().getPointsValuation())
                        .average()
                        .orElse(0.0);

        final List<Violation> violations = getSubViolations(entryList);

        Rating rating = points >= validationConfig.getFeature().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return FeatureValidatorEntry
                .builder()
                .feature(feature)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .userStoryValidatorEntry(userStoryValidatorEntry)
                .acceptanceCriteriaValidatorEntry(acceptanceCriteriaValidatorEntry)
                .estimationValidatorEntry(estimationValidatorEntry)
                .build();
    }

    private static List<Violation> getSubViolations(List<Map.Entry<Boolean, AbstractStoryalidatorEntryItem>> entryList) {
        return entryList.stream()
                    .filter(Map.Entry::getKey)
                    .map(Map.Entry::getValue)
                    .map(AbstractScorable::getViolations)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }

}
