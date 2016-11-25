package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.validatorentry.*;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.craftsmenlabs.stories.ranking.Ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BacklogScorer {

    /**
     * Performs scoring on the backlog which was extracted from the source.
     * It checks all features, bugs and epics on the backlog. If the scoring is good enough, we will mark it as success, otherwise we will mark it as FAIL.
     *
     * @param backlog          Backlog to validate
     * @param ranking          Ranking model to use
     * @param validationConfig Config op the validation
     * @return Validated backlog
     */
    public static BacklogValidatorEntry performScorer(Backlog backlog, Ranking ranking, ValidationConfig validationConfig) {
        BacklogValidatorEntry backlogValidatorEntry = BacklogValidatorEntry.builder()
                .backlog(backlog)
                .bugValidatorEntries(new BacklogItemList<>())
                .featureValidatorEntries(new BacklogItemList<>())
                .epicValidatorEntries(new BacklogItemList<>())
                .violations(new ArrayList<>())
                .build();


        if ( backlog == null || backlog.getAllItems().size()==0 ) {
            backlogValidatorEntry.getViolations().add(new Violation(
                    ViolationType.BacklogEmptyViolation,
                    "The backlog is empty, or doesn't contain any issues."
            ));

            backlogValidatorEntry.setPointsValuation(0f);
            backlogValidatorEntry.setRating(Rating.FAIL);
            return backlogValidatorEntry;
        }
        List<FeatureValidatorEntry> features = new ArrayList<>();
        List<BugValidatorEntry> bugs = new ArrayList<>();
        List<EpicValidatorEntry> epics = new ArrayList<>();

        // Feature scores
        if (validationConfig.getFeature().isActive()) {
            features = getValidatedFeatures(backlog, validationConfig);

            backlogValidatorEntry.setFeatureValidatorEntries(
                    BacklogItemList.<FeatureValidatorEntry>builder()
                            .items(features)
                            .isActive(validationConfig.getFeature().isActive())
                            .build());
        }

        // Bug scores
        if (validationConfig.getBug().isActive()) {
            bugs = getValidatedBugs(backlog, validationConfig);

            backlogValidatorEntry.setBugValidatorEntries(
                    BacklogItemList.<BugValidatorEntry>builder()
                            .items(bugs)
                            .isActive(validationConfig.getBug().isActive())
                            .build());
        }

        // Epic scores
        if (validationConfig.getEpic().isActive()) {
            epics = getValidatedEpics(backlog, validationConfig);

            backlogValidatorEntry.setEpicValidatorEntries(
                    BacklogItemList.<EpicValidatorEntry>builder()
                            .items(epics)
                            .isActive(validationConfig.getEpic().isActive())
                            .build());
        }

        // Backlog scores
        List<? super BacklogItem> scoredEntries = new ArrayList<>(features);
        scoredEntries.addAll(bugs);
        scoredEntries.addAll(epics);

        float backlogPoints = ranking.createRanking(scoredEntries);
        backlogValidatorEntry.setPointsValuation(backlogPoints);

        if (backlogValidatorEntry.getPointsValuation() * 100f >= validationConfig.getBacklog().getRatingThreshold()) {
            backlogValidatorEntry.setRating(Rating.SUCCESS);
        } else {
            // Failed, add violation
            backlogValidatorEntry.setRating(Rating.FAIL);
            backlogValidatorEntry.getViolations().add(new Violation(
                    ViolationType.BacklogRatingViolation,
                    "The backlog did not score a minimum of " + validationConfig.getBacklog().getRatingThreshold()
                            + " points and is therefore rated: " + backlogValidatorEntry.getRating()));
        }

        backlogValidatorEntry.setFeatureValidatorEntries(
                BacklogItemList.<FeatureValidatorEntry>builder()
                        .items(features)
                        .isActive(validationConfig.getBug().isActive())
                        .build());
        backlogValidatorEntry.setBugValidatorEntries(
                BacklogItemList.<BugValidatorEntry>builder()
                        .items(bugs)
                        .isActive(validationConfig.getBug().isActive())
                        .build());
        backlogValidatorEntry.setEpicValidatorEntries(
                BacklogItemList.<EpicValidatorEntry>builder()
                        .items(epics)
                        .isActive(validationConfig.getBug().isActive())
                        .build());
        return backlogValidatorEntry;
    }

    private static List<FeatureValidatorEntry> getValidatedFeatures(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getFeatures().stream()
                .map(feature -> FeatureScorer.performScorer(feature, validationConfig))
                .collect(Collectors.toList());
    }

    private static List<BugValidatorEntry> getValidatedBugs(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getBugs().stream()
                .map(bug -> BugScorer.performScorer(bug, validationConfig))
                .collect(Collectors.toList());
    }

    private static List<EpicValidatorEntry> getValidatedEpics(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getEpics().stream()
                .map(epic -> EpicScorer.performScorer(epic, validationConfig))
                .collect(Collectors.toList());
    }
}
