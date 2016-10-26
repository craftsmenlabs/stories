package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.api.models.violation.BacklogViolation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.craftsmenlabs.stories.ranking.Ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BacklogScorer {

    public static BacklogValidatorEntry performScorer(Backlog backlog, Ranking ranking, ScorerConfigCopy validationConfig ) {
        BacklogValidatorEntry backlogValidatorEntry =
                BacklogValidatorEntry.builder()
                        .backlog(backlog)
                        .violations(new ArrayList<>())
                        .build();


        if (backlog == null || backlog.getIssues() == null || backlog.getIssues().size() == 0) {
            backlogValidatorEntry.getViolations()
                    .add(new BacklogViolation(
                            ViolationType.BacklogEmptyViolation,
                            "The backlog is empty, or doesn't contain any issues."
                    ));

            backlogValidatorEntry.setPointsValuation(0f);

        } else {

            backlogValidatorEntry.setIssueValidatorEntries(getValidatedIssues(backlog, validationConfig));


            Float points = ranking.createRanking(backlogValidatorEntry);
            backlogValidatorEntry.setPointsValuation(points);
        }

        if (backlogValidatorEntry.getPointsValuation() * 100f >= validationConfig.getBacklog().getRatingtreshold())
            backlogValidatorEntry.setRating(Rating.SUCCES);
        else {
            backlogValidatorEntry.setRating(Rating.FAIL);
            backlogValidatorEntry.getViolations()
                    .add(new BacklogViolation(
                            ViolationType.BacklogRatingViolation,
                            "The backlog did not score a minimum of "
                                    + validationConfig.getBacklog().getRatingtreshold()
                                    + " points and is therefore rated: "
                                    + backlogValidatorEntry.getRating()));
        }
        return backlogValidatorEntry;
    }

    private static List<IssueValidatorEntry> getValidatedIssues(Backlog backlog, ScorerConfigCopy validationConfig) {
        return backlog.getIssues().stream()
                .map(issue -> IssueScorer.performScorer(issue, validationConfig))
                .collect(Collectors.toList());
    }
}
