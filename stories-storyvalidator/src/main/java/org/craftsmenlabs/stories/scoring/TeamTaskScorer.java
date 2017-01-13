package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.TeamTask;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEstimation;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedTeamTask;
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

    public static ValidatedTeamTask performScorer(TeamTask teamTask, ValidationConfig validationConfig) {
        List<Violation> violations = new ArrayList<>();
        float points = 0f;
        float pointsRatio = 1f / Stream.of(
                    !"summary".isEmpty(),
                    !"description".isEmpty(),
                    validationConfig.getCriteria().isActive(),
                    validationConfig.getEstimation().isActive())
                .filter(i->i)
                .count();


        if (teamTask == null) {
            teamTask = new TeamTask("0", "0", "", "", "", "", 0f);
        }

        if (StringUtils.isEmpty(teamTask.getSummary())) {
            teamTask.setSummary("");
            violations.add(new Violation(ViolationType.TeamTaskSummaryEmptyViolation, "No summary was given."));
        }else{
            points += pointsRatio;
        }
        if (StringUtils.isEmpty(teamTask.getDescription())) {
            teamTask.setDescription("");
            violations.add(new Violation(ViolationType.TeamTaskDescriptionEmptyViolation, "No description was given."));
        } else {
            points += pointsRatio;
        }

        ValidatedAcceptanceCriteria validatedAcceptanceCriteria = AcceptanceCriteriaScorer.performScorer(teamTask.getAcceptationCriteria(), validationConfig);
        if (teamTask.getAcceptationCriteria() != null && validationConfig.getCriteria().isActive()) {
            violations.addAll(validatedAcceptanceCriteria.getViolations());
            points += pointsRatio * validatedAcceptanceCriteria.getPointsValuation();
        }

        ValidatedEstimation validatedEstimation = EstimationScorer.performScorer(teamTask.getEstimation(), validationConfig);
        if (teamTask.getEstimation() != null && validationConfig.getEstimation().isActive()) {
            violations.addAll(validatedEstimation.getViolations());
            points += pointsRatio * validatedEstimation.getPointsValuation();
        }

        Rating rating = points >= validationConfig.getTeamTask().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedTeamTask
                .builder()
                .teamTask(teamTask)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .validatedAcceptanceCriteria(validatedAcceptanceCriteria)
                .validatedEstimation(validatedEstimation)
                .build();
    }

}
