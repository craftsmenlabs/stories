package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.ranking.Ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BacklogScorer {

    //static ScorerConfig cfg = ConfigFactory.create(ScorerConfig.class, System.getenv());

    public static BacklogValidatorEntry performScorer(Backlog backlog, Ranking ranking, ScorerConfigCopy validationConfig ) {


        List<IssueValidatorEntry> issueValidatorEntries = getValidatedIssues(backlog, validationConfig);

        BacklogValidatorEntry backlogValidatorEntry =
                BacklogValidatorEntry.builder()
                        .backlog(backlog)
                        .violations(new ArrayList<>())
                        .issueValidatorEntries(issueValidatorEntries)
                        .build();

        Float points = ranking.createRanking(backlogValidatorEntry);
        backlogValidatorEntry.setPointsValuation(points);

        backlogValidatorEntry.setRating(points * 100f >= validationConfig.getBacklog().getRatingtreshold() ? Rating.SUCCES : Rating.FAIL);

        return backlogValidatorEntry;
    }

    private static List<IssueValidatorEntry> getValidatedIssues(Backlog backlog, ScorerConfigCopy validationConfig) {
        return backlog.getIssues().stream()
                .map(issue -> IssueScorer.performScorer(issue, validationConfig))
                .collect(Collectors.toList());
    }
}
