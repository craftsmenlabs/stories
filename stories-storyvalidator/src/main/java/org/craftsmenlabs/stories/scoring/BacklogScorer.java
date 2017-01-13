package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.Backlog;
import org.craftsmenlabs.stories.api.models.items.validated.*;
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
                .teamTaskValidatorEntries(new BacklogItemList<>())
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
        List<ValidatedFeature> features = new ArrayList<>();
        List<ValidatedBug> bugs = new ArrayList<>();
        List<ValidatedEpic> epics = new ArrayList<>();
        List<ValidatedTeamTask> teamTasks = new ArrayList<>();

        // Feature scores
        if (validationConfig.getFeature().isActive() ) {
            features = getValidatedFeatures(backlog, validationConfig);
        }
        backlogValidatorEntry.setFeatureValidatorEntries(
                BacklogItemList.<ValidatedFeature>builder()
                        .items(features)
                        .isActive(validationConfig.getFeature().isActive())
                        .build());


        // Bug scores
        if (validationConfig.getBug().isActive()) {
            bugs = getValidatedBugs(backlog, validationConfig);
        }
        backlogValidatorEntry.setBugValidatorEntries(
                BacklogItemList.<ValidatedBug>builder()
                        .items(bugs)
                        .isActive(validationConfig.getBug().isActive())
                        .build());


        // Epic scores
        if (validationConfig.getEpic().isActive()) {
            epics = getValidatedEpics(backlog, validationConfig);
        }
        backlogValidatorEntry.setEpicValidatorEntries(
                BacklogItemList.<ValidatedEpic>builder()
                        .items(epics)
                        .isActive(validationConfig.getEpic().isActive())
                        .build());


        // Team tasks scores
        if (validationConfig.getTeamTask().isActive()) {
            teamTasks = getValidatedTeamTasks(backlog, validationConfig);

            backlogValidatorEntry.setTeamTaskValidatorEntries(
                    BacklogItemList.<ValidatedTeamTask>builder()
                            .items(teamTasks)
                            .isActive(validationConfig.getTeamTask().isActive())
                            .build());
        }

        // Backlog scores
        List<? super ValidatedBacklogItem> scoredEntries = new ArrayList<>(features);
        scoredEntries.addAll(bugs);
        scoredEntries.addAll(epics);
        scoredEntries.addAll(teamTasks);

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


//        //TODO everytime config.getBUg?????
//        backlogValidatorEntry.setFeatureValidatorEntries(
//                BacklogItemList.<ValidatedFeature>builder()
//                        .items(features)
//                        .isActive(validationConfig.getBug().isActive())
//                        .build());
//        backlogValidatorEntry.setBugValidatorEntries(
//                BacklogItemList.<ValidatedBug>builder()
//                        .items(bugs)
//                        .isActive(validationConfig.getBug().isActive())
//                        .build());
//        backlogValidatorEntry.setEpicValidatorEntries(
//                BacklogItemList.<ValidatedEpic>builder()
//                        .items(epics)
//                        .isActive(validationConfig.getBug().isActive())
//                        .build());
//        backlogValidatorEntry.setTeamTaskValidatorEntries(
//                BacklogItemList.<ValidatedTeamTask>builder()
//                        .items(teamTasks)
//                        .isActive(validationConfig.getTeamTask().isActive())
//                        .build());
        return backlogValidatorEntry;
    }

    private static List<ValidatedFeature> getValidatedFeatures(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getFeatures().stream()
                .map(feature -> FeatureScorer.performScorer(feature, validationConfig))
                .collect(Collectors.toList());
    }

    private static List<ValidatedBug> getValidatedBugs(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getBugs().stream()
                .map(bug -> BugScorer.performScorer(bug, validationConfig))
                .collect(Collectors.toList());
    }

    private static List<ValidatedEpic> getValidatedEpics(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getEpics().stream()
                .map(epic -> EpicScorer.performScorer(epic, validationConfig))
                .collect(Collectors.toList());
    }

    private static List<ValidatedTeamTask> getValidatedTeamTasks(Backlog backlog, ValidationConfig validationConfig) {
        return backlog.getTeamTasks().stream()
                .map(task -> TeamTaskScorer.performScorer(task, validationConfig))
                .collect(Collectors.toList());
    }
}
