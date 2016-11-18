package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.FeatureValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.craftsmenlabs.stories.ranking.Ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BacklogScorer {

    public static BacklogValidatorEntry performScorer(Backlog backlog, Ranking ranking, ValidationConfig validationConfig) {
        BacklogValidatorEntry backlogValidatorEntry =
                BacklogValidatorEntry.builder()
                        .backlog(backlog)
                        .violations(new ArrayList<>())
                        .build();


        if (backlog == null || backlog.getFeatures() == null || backlog.getFeatures().size() == 0) {
            backlogValidatorEntry.getViolations()
                    .add(new Violation(
                            ViolationType.BacklogEmptyViolation,
                            "The backlog is empty, or doesn't contain any features."
                    ));

            backlogValidatorEntry.setPointsValuation(0f);
            backlogValidatorEntry.setRating(Rating.FAIL);
            return backlogValidatorEntry;

        } else {

            backlogValidatorEntry.setFeatureValidatorEntries(getValidatedFeatures(backlog, validationConfig));


            Float points = ranking.createRanking(backlogValidatorEntry);
            backlogValidatorEntry.setPointsValuation(points);
        }

        if (backlogValidatorEntry.getPointsValuation() * 100f >= validationConfig.getBacklog().getRatingtreshold())
            backlogValidatorEntry.setRating(Rating.SUCCESS);
        else {
            backlogValidatorEntry.setRating(Rating.FAIL);
            backlogValidatorEntry.getViolations()
                    .add(new Violation(
                            ViolationType.BacklogRatingViolation,
                            "The backlog did not score a minimum of "
                                    + validationConfig.getBacklog().getRatingtreshold()
                                    + " points and is therefore rated: "
                                    + backlogValidatorEntry.getRating()));
        }
        return backlogValidatorEntry;
    }

    private static List<FeatureValidatorEntry> getValidatedFeatures(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getFeatures().stream()
                .map(feature -> IssueScorer.performScorer(feature, validationConfig))
                .collect(Collectors.toList());
    }
}
