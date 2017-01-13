package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.TeamTask;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedTeamTask;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTaskScorerTest {

    @Test
    public void testPerformScorerReturnsZeroOnEmpty(@Injectable ValidatedTeamTask entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem();
            result = new TeamTask();

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7f;
        }};

        float score = TeamTaskScorer.performScorer(entry.getItem(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable ValidatedTeamTask entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem();
            result = null;

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7f;
        }};

        float score = TeamTaskScorer.performScorer(entry.getItem(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerReturnsOneOnPerfect(@Injectable ValidatedTeamTask entry, @Injectable ValidationConfig validationConfig) throws Exception {
        TeamTask teamTask = TeamTask.builder()
                .summary("summary")
                .description("description")
                .acceptationCriteria(StringUtils.repeat("Given when then", 20))
                .estimation(1f)
                .build();
        new Expectations() {{
            entry.getItem();
            result = teamTask;

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7f;
        }};

        float score = TeamTaskScorer.performScorer(entry.getItem(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(1.0f);
    }

}
