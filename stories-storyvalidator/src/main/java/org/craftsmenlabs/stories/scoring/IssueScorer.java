package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.*;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Assigns points to an issue, based on all
 * underlying fields, such as user story, acceptance criteria, estimated points
 */
public class IssueScorer {

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

        UserStoryValidatorEntry userStoryValidatorEntry = StoryScorer.performScorer(feature.getUserstory(), validationConfig);
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(feature.getAcceptanceCriteria(), validationConfig);
        EstimationValidatorEntry estimationValidatorEntry = EstimationScorer.performScorer(feature.getEstimation(), validationConfig);

        float points = (float)
                Stream.of(userStoryValidatorEntry, acceptanceCriteriaValidatorEntry, estimationValidatorEntry)
                        .filter(AbstractValidatorEntry::isActive)
                        .mapToDouble(AbstractValidatorEntry::getPointsValuation)
                        .average()
                        .orElse(0.0);

        Rating rating = points >= validationConfig.getIssue().getRatingtreshold() ? Rating.SUCCESS : Rating.FAIL;

        return FeatureValidatorEntry
                .builder()
                .feature(feature)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .rating(rating)
                .userStoryValidatorEntry(userStoryValidatorEntry)
                .acceptanceCriteriaValidatorEntry(acceptanceCriteriaValidatorEntry)
                .estimationValidatorEntry(estimationValidatorEntry)
                .build();
    }

}
