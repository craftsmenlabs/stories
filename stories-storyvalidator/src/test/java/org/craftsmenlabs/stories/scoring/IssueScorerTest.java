package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.UserStoryValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class IssueScorerTest {

    @Test
    public void performScorer_ReturnsZeroOnNullIssue(@Injectable Issue issue, @Injectable ScorerConfigCopy validationConfig) {
        float score = IssueScorer.performScorer(null, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void performScorer_ReturnsZeroOnNullUserStory(@Injectable Issue issue, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations(){{
           issue.getUserstory();
           result = null;

        }};
        float score = IssueScorer.performScorer(issue, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void performScorer_ReturnsZeroOnNullCriteria(@Injectable Issue issue, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations(){{
            issue.getUserstory();
            result = "";

            issue.getAcceptanceCriteria();
           result = null;

        }};
        float score = IssueScorer.performScorer(issue, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Test
    public void performScorer_ReturnsZeroOnAllNotActive(@Injectable Issue issue, @Injectable ScorerConfigCopy validationConfig) {
        new Expectations(){{
            issue.getUserstory();
            result = "";

            validationConfig.getStory().isActive();
            result = false;

            issue.getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().isActive();
            result = false;


            issue.getEstimation();
            result = null;
            validationConfig.getEstimation().isActive();
            result = false;

        }};
        float score = IssueScorer.performScorer(issue, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0f, withinPercentage(1));
    }

    @Ignore(value = "TODO fix injecting expectations")
    @Test
    public void performScorer_ReturnsOneOnOnlyPerfectUerstoryActive(@Injectable Issue issue, @Injectable ScorerConfigCopy validationConfig) {
        UserStoryValidatorEntry entry = UserStoryValidatorEntry.builder().pointsValuation(1f).userStory("").build();
        new Expectations(){{
//            issue.getUserstory();
//            result = "";
//
            StoryScorer.performScorer("", validationConfig);
            result = entry;

            validationConfig.getStory().isActive();
            result = true;

            issue.getAcceptanceCriteria();
            result = "";

            validationConfig.getCriteria().isActive();
            result = false;

            issue.getEstimation();
            result = null;

            validationConfig.getEstimation().isActive();
            result = false;

        }};
        float score = IssueScorer.performScorer(issue, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(1f, withinPercentage(1));
    }




}
