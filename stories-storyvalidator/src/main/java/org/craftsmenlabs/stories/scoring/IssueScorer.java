package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.EstimationValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;

import java.util.ArrayList;

/**
 * Assigns points to an issue, based on all
 * underlying fields, such as user story, acceptancecriteria, estimated points
 */
public class IssueScorer {

    public static IssueValidatorEntry performScorer(Issue issue, ScorerConfigCopy validationConfig) {
        UserStoryValidatorEntry userStoryValidatorEntry = StoryScorer.performScorer(issue.getUserstory(), validationConfig);
        AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry = AcceptanceCriteriaScorer.performScorer(issue.getAcceptanceCriteria(), validationConfig);
        EstimationValidatorEntry estimationValidatorEntry = EstimationScorer.performScorer(issue.getEstimation(), validationConfig);

        float points =
                (userStoryValidatorEntry.getPointsValuation()
                + acceptanceCriteriaValidatorEntry.getPointsValuation())
                / 2;

        Rating rating = points >= validationConfig.getIssue().getRatingtreshold()? Rating.SUCCES : Rating.FAIL;

        return IssueValidatorEntry
                .builder()
                .issue(issue)
                .violations(new ArrayList<>())
                .pointsValuation(points)
                .rating(rating)
                .userStoryValidatorEntry(userStoryValidatorEntry)
                .acceptanceCriteriaValidatorEntry(acceptanceCriteriaValidatorEntry)
                .estimationValidatorEntry(estimationValidatorEntry)
                .build();
    }
}
