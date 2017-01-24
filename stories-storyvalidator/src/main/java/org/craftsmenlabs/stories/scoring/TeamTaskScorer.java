package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.TeamTask;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.TeamTaskValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Assigns points to a teamTask, based on all
 * underlying fields, such as user story, acceptance criteria, estimated points
 */
public class TeamTaskScorer {

    public static TeamTaskValidatorEntry performScorer(TeamTask teamTask, ValidationConfig validationConfig) {
        List<Violation> violations = new ArrayList<>();
        float pointsRatio = 1f / Stream.of(
                    !"summary".isEmpty(),
                    !"description".isEmpty(),
                    validationConfig.getCriteria().isActive(),
                    validationConfig.getEstimation().isActive())
                .filter(i->i)
                .count();
        float points = 0f;

        if (teamTask == null) {
            teamTask = new TeamTask("0", "0", "", "", "", "", 0f);
        }

        if (StringUtils.isEmpty(teamTask.getSummary())) {
            teamTask.setSummary("");
            violations.add(new Violation(
                    ViolationType.TeamTaskSummaryEmptyViolation,
                    "No summary was given.",
                    pointsRatio));
        } else {
            points += pointsRatio;
        }

        if (StringUtils.isEmpty(teamTask.getDescription())) {
            teamTask.setDescription("");
            violations.add(new Violation(
                    ViolationType.TeamTaskDescriptionEmptyViolation,
                    "No description was given.",
                    pointsRatio));
        } else {
            points += pointsRatio;
        }


        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(teamTask.getAcceptationCriteria(), validationConfig);
        if (teamTask.getAcceptationCriteria() != null && validationConfig.getCriteria().isActive()) {
            acceptanceCriteriaValidatorEntry.getViolations().forEach(violation -> violation.setPoints(pointsRatio * violation.getPoints()));
            violations.addAll(acceptanceCriteriaValidatorEntry.getViolations());
            points += acceptanceCriteriaValidatorEntry.getPointsValuation();
        }

        EstimationValidatorEntry estimationValidatorEntry = EstimationScorer.performScorer(teamTask.getEstimation(), validationConfig);
        if (teamTask.getEstimation() != null && validationConfig.getEstimation().isActive()) {
            estimationValidatorEntry .getViolations().forEach(violation -> violation.setPoints(pointsRatio * violation.getPoints()));
            violations.addAll(estimationValidatorEntry.getViolations());
            points += estimationValidatorEntry.getPointsValuation();
        }

        Rating rating = points >= validationConfig.getTeamTask().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return TeamTaskValidatorEntry
                .builder()
                .teamTask(teamTask)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .acceptanceCriteriaValidatorEntry(acceptanceCriteriaValidatorEntry)
                .estimationValidatorEntry(estimationValidatorEntry)
                .build();
    }
}