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
        super(1, validationConfig);
    }

    public TeamTaskScorer(double potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
    }

    @Override
    public ValidatedTeamTask validate(TeamTask teamTask) {
        double pointsPerSubItem = potentialPoints / Stream.of(
                !"summary".isEmpty(),
                !"description".isEmpty(),
                validationConfig.getCriteria().isActive(),
                validationConfig.getEstimation().isActive())
                .filter(i -> i)
                .count();
        double points = 0.0;

        if (teamTask == null) {
            teamTask = TeamTask.empty();
        }

        List<Violation> violations = new ArrayList<>();
        if (StringUtils.isEmpty(teamTask.getSummary())) {
            violations.add(new Violation(
                    ViolationType.TeamTaskSummaryEmptyViolation,
                    "No summary was given.",
                    pointsPerSubItem));
        } else {
            points += pointsPerSubItem;
        }

        if (StringUtils.isEmpty(teamTask.getDescription())) {
            teamTask.setDescription("");
            violations.add(new Violation(
                    ViolationType.TeamTaskDescriptionEmptyViolation,
                    "No description was given.",
                    pointsPerSubItem));
        } else {
            points += pointsPerSubItem;
        }

        ValidatedAcceptanceCriteria acceptanceCriteriaValidatorEntry = new AcceptanceCriteriaScorer(pointsPerSubItem, validationConfig).validate(teamTask.getAcceptationCriteria());
        if (teamTask.getAcceptationCriteria() != null && validationConfig.getCriteria().isActive()) {
//            acceptanceCriteriaValidatorEntry.getViolations().forEach(violation -> violation.setPoints(1.0, pointsPerSubItem));
            points += acceptanceCriteriaValidatorEntry.getScoredPoints();
        }

        ValidatedEstimation estimationValidatorEntry = new EstimationScorer(pointsPerSubItem, validationConfig).validate(teamTask.getEstimation());
        if (validationConfig.getEstimation().isActive()) {
//            estimationValidatorEntry.getViolations().forEach(violation -> violation.setPoints(1.0, pointsPerSubItem));
            points += estimationValidatorEntry.getScoredPoints();
        }


        ValidatedTeamTask validatedTeamTask = ValidatedTeamTask
                .builder()
                .teamTask(teamTask)
                .violations(violations)
                .validatedAcceptanceCriteria(acceptanceCriteriaValidatorEntry)
                .validatedEstimation(estimationValidatorEntry)
                .build();

        validatedTeamTask.setPoints(points, potentialPoints);
        Rating rating = validatedTeamTask.getScoredPercentage() >= validationConfig.getTeamTask().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;
        validatedTeamTask.setRating(rating);

        return validatedTeamTask;
    }
}