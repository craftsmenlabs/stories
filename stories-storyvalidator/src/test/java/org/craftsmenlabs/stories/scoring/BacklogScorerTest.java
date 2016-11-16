package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.ranking.Ranking;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BacklogScorerTest {
    @Mocked
    Backlog backlog;

    @Mocked
    ValidationConfigCopy validationConfigCopy;

    @Test
    public void testPerformScorerReturnsSuccesonOnScoreExactlyOnTreshold(@Injectable Ranking ranking) throws Exception {
        List<Issue> issues = Arrays.asList(
                new Issue()
        );

        new Expectations(){{
            backlog.getIssues();
            result = issues;

            ranking.createRanking(withNotNull());
            result = 0.5f;

            validationConfigCopy.getBacklog().getRatingtreshold();
            result = 50f;
        }};

        BacklogValidatorEntry result = BacklogScorer.performScorer(backlog, ranking, validationConfigCopy);
        assertThat(result.getRating()).isEqualTo(Rating.SUCCESS);
    }

    @Test
    public void testPerformScorerReturnsFailonZeroBacklogScore(@Injectable Ranking ranking) throws Exception {
        List<Issue> issues = Arrays.asList(
                new Issue()
        );

        new Expectations(){{
            backlog.getIssues();
            result = issues;

            ranking.createRanking(withNotNull());
            result = 0f;

            validationConfigCopy.getBacklog().getRatingtreshold();
            result = 50f;
        }};

        BacklogValidatorEntry result = BacklogScorer.performScorer(backlog, ranking, validationConfigCopy);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerFailOnEmptyBacklog(@Injectable Ranking ranking) {
        BacklogValidatorEntry result = BacklogScorer.performScorer(null, ranking, validationConfigCopy);

        assertThat(result.getPointsValuation()).isEqualTo(0f);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);

    }
}
