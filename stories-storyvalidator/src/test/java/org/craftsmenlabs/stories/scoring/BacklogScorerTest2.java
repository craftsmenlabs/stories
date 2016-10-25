package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.ranking.Ranking;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BacklogScorerTest2 {
    @Tested
    BacklogScorer backlogScorer;

    @Mocked
    Backlog backlog;

    @Mocked
    ScorerConfigCopy scorerConfigCopy;

    @Test
    public void testPerformScorerReturnsSuccesonOnScoreExactlyOnTreshold(@Injectable BacklogValidatorEntry backlogValidatorEntry, @Injectable Ranking ranking){
        List<Issue> issues = Arrays.asList(
                new Issue()
        );

        new Expectations(){{
            backlog.getIssues();
            result = issues;

            ranking.createRanking(withNotNull());
            result = 0.5f;

            scorerConfigCopy.getBacklog().getRatingtreshold();
            result = 50f;
        }};

        BacklogValidatorEntry result = backlogScorer.performScorer(backlog, ranking, scorerConfigCopy);
        assertThat(result.getRating()).isEqualTo(Rating.SUCCES);
    }

    @Test
    public void testPerformScorerReturnsFailonZeroBacklogScore(@Injectable BacklogValidatorEntry backlogValidatorEntry, @Injectable Ranking ranking){
        List<Issue> issues = Arrays.asList(
                new Issue()
        );

        new Expectations(){{
            backlog.getIssues();
            result = issues;

            ranking.createRanking(withNotNull());
            result = 0f;

            scorerConfigCopy.getBacklog().getRatingtreshold();
            result = 50f;
        }};

        BacklogValidatorEntry result = backlogScorer.performScorer(backlog, ranking, scorerConfigCopy);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }
}
