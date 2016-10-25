package org.craftsmenlabs.stories.scoring;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.ranking.Ranking;

public class BacklogScorer {

    public static BacklogValidatorEntry performScorer(Backlog backlog, Ranking ranking, ScorerConfigCopy validationConfig ) {
        if (backlog == null || backlog.getIssues() == null || backlog.getIssues().size() == 0)
        {
            throw new IllegalArgumentException("Scoring can only be performed on a filled backlog.");
        }

        List<IssueValidatorEntry> issueValidatorEntries = getValidatedIssues(backlog, validationConfig);

        BacklogValidatorEntry backlogValidatorEntry =
                BacklogValidatorEntry.builder()
                        .backlog(backlog)
                        .violations(new ArrayList<>())
                        .issueValidatorEntries(issueValidatorEntries)
                        .build();

        Float points = ranking.createRanking(backlogValidatorEntry);
        backlogValidatorEntry.setPointsValuation(points);

        rateScore(validationConfig, backlogValidatorEntry);

        return backlogValidatorEntry;
    }

    private static void rateScore(ScorerConfigCopy validationConfig, BacklogValidatorEntry backlogValidatorEntry) {
        backlogValidatorEntry.setRating(backlogValidatorEntry.getPointsValuation() * 100f >= validationConfig.getBacklog().getRatingtreshold() ? Rating.SUCCES : Rating.FAIL);
    }

    private static List<IssueValidatorEntry> getValidatedIssues(Backlog backlog, ScorerConfigCopy validationConfig) {
        return backlog.getIssues().stream()
                .map(issue -> IssueScorer.performScorer(issue, validationConfig))
                .collect(Collectors.toList());
    }
}
