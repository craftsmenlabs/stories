package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.validatorentry.*;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.craftsmenlabs.stories.ranking.Ranking;

import java.util.ArrayList;
import java.util.LinkedList;
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
                .bugValidatorEntries(new LinkedList<>())
                .featureValidatorEntries(new LinkedList<>())
                .epicValidatorEntries(new LinkedList<>())
                .violations(new ArrayList<>())
                .build();


        if (backlog == null
                || (validationConfig.getFeature().isActive() && (backlog.getFeatures() == null || backlog.getFeatures().size() == 0))
                || (validationConfig.getBug().isActive() && (backlog.getBugs() == null || backlog.getBugs().size() == 0))

                ) {
            backlogValidatorEntry.getViolations().add(new Violation(
                    ViolationType.BacklogEmptyViolation,
                    "The backlog is empty, or doesn't contain any issues."
            ));

            backlogValidatorEntry.setAverageScore(0f);
            backlogValidatorEntry.setRating(Rating.FAIL);
            return backlogValidatorEntry;
        }

        List<AbstractValidatorEntry> scoredEntries = new LinkedList<>();

        // Feature scores
        if (validationConfig.getFeature().isActive()) {
            List<FeatureValidatorEntry> features = getValidatedFeatures(backlog, validationConfig);
            backlogValidatorEntry.setFeatureValidatorEntries(features);
            scoredEntries.addAll(features);

            // Determine feature points
            Float featurePoints = ranking.createRanking(features.stream().map(feature -> (AbstractValidatorEntry) feature).collect(Collectors.toList()));
            backlogValidatorEntry.setFeatureScore(featurePoints);

        }

        // Bug scores
        if (validationConfig.getBug().isActive()) {
            List<BugValidatorEntry> bugs = getValidatedBugs(backlog, validationConfig);
            backlogValidatorEntry.setBugValidatorEntries(bugs);
            scoredEntries.addAll(bugs);

            // Determine bug points
            Float bugPoints = ranking.createRanking(bugs.stream().map(bug -> (AbstractValidatorEntry) bug).collect(Collectors.toList()));
            backlogValidatorEntry.setBugScore(bugPoints);
        }

        if (validationConfig.getEpic().isActive()) {
            List<EpicValidatorEntry> epics = getValidatedEpics(backlog, validationConfig);
            backlogValidatorEntry.setEpicValidatorEntries(epics);
            scoredEntries.addAll(epics);

            Float epicPoints = ranking.createRanking(epics.stream().map(epic -> (AbstractValidatorEntry) epic).collect(Collectors.toList()));
            backlogValidatorEntry.setEpicScore(epicPoints);
        }


        // Backlog scores
        float backlogPoints = ranking.createRanking(scoredEntries);
        backlogValidatorEntry.setAverageScore(backlogPoints);

        if (backlogValidatorEntry.getAverageScore() * 100f >= validationConfig.getBacklog().getRatingtreshold()) {
            backlogValidatorEntry.setRating(Rating.SUCCESS);
        } else {
            // Failed, add violation
            backlogValidatorEntry.setRating(Rating.FAIL);
            backlogValidatorEntry.getViolations().add(new Violation(
                    ViolationType.BacklogRatingViolation,
                    "The backlog did not score a minimum of " + validationConfig.getBacklog().getRatingtreshold()
                            + " points and is therefore rated: " + backlogValidatorEntry.getRating()));
        }
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
