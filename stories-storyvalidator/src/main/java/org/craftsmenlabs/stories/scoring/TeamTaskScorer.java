package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.TeamTask;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedTeamTask;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Assigns scoredPercentage to a teamTask, based on all
 * underlying fields, such as user story, acceptance criteria, estimated scoredPercentage
 */
public class TeamTaskScorer extends AbstractScorer<TeamTask, ValidatedTeamTask> {

    public TeamTaskScorer(ValidationConfig validationConfig) {
        super(validationConfig);
    }

    @Override
    public ValidatedTeamTask validate(TeamTask teamTask) {
        List<Violation> violations = new ArrayList<>();
        float pointsRatio = 1f / Stream.of(
                !"summary".isEmpty(),
                !"description".isEmpty(),
                validationConfig.getCriteria().isActive(),
                validationConfig.getEstimation().isActive())
                .filter(i -> i)
                .count();
        float points = 0f;

        if (teamTask == null) {
            teamTask = new TeamTask("0", "0", "", null, "", "", "", null, null, pointsRatio);
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


        ValidatedAcceptanceCriteria acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(teamTask.getAcceptationCriteria(), pointsRatio, validationConfig);
        if (teamTask.getAcceptationCriteria() != null && validationConfig.getCriteria().isActive()) {
            acceptanceCriteriaValidatorEntry.getViolations().forEach(violation -> violation.setScoredPercentage(pointsRatio * violation.getScoredPercentage()));
            violations.addAll(acceptanceCriteriaValidatorEntry.getViolations());
            points += acceptanceCriteriaValidatorEntry.getScoredPoints();
        }

        ValidatedEstimation estimationValidatorEntry = EstimationScorer.performScorer(teamTask.getEstimation(), pointsRatio, validationConfig);
        if (teamTask.getEstimation() != null && validationConfig.getEstimation().isActive()) {
            estimationValidatorEntry.getViolations().forEach(violation -> violation.setScoredPercentage(pointsRatio * violation.getScoredPercentage()));
            violations.addAll(estimationValidatorEntry.getViolations());
            points += estimationValidatorEntry.getScoredPoints();
        }

        Rating rating = points >= validationConfig.getTeamTask().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedTeamTask
                .builder()
                .teamTask(teamTask)
                .violations(violations)
                .rating(rating)
                .validatedAcceptanceCriteria(acceptanceCriteriaValidatorEntry)
                .validatedEstimation(estimationValidatorEntry)
                .scoredPoints(points)
                .missedPoints(teamTask.getPotentialPoints() - points)
                .scoredPercentage(points / teamTask.getPotentialPoints())
                .missedPercentage(1f - (points / teamTask.getPotentialPoints()))
                .build();
    }
}