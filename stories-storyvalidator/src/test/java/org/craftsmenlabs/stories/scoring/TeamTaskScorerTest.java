package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.TeamTask;
import org.craftsmenlabs.stories.api.models.validatorentry.TeamTaskValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTaskScorerTest {

    @Test
    public void testPerformScorerReturnsZeroOnEmpty(@Injectable TeamTaskValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getTeamTask();
            result = new TeamTask();

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7f;
        }};

        float score = TeamTaskScorer.performScorer(entry.getTeamTask(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerReturnsNullOnEmpty(@Injectable TeamTaskValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getTeamTask();
            result = null;

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7f;
        }};

        float score = TeamTaskScorer.performScorer(entry.getTeamTask(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorerReturnsOneOnPerfect(@Injectable TeamTaskValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        TeamTask teamTask = TeamTask.builder()
                .summary("summary")
                .description("description")
                .acceptationCriteria(StringUtils.repeat("Given when then", 20))
                .estimation(1f)
                .build();
        new Expectations() {{
            entry.getTeamTask();
            result = teamTask;

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7f;
        }};

        float score = TeamTaskScorer.performScorer(entry.getTeamTask(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(1.0f);
    }

    @Test
    public void testPerformScorerAddsSubViolationsToTask(@Injectable TeamTaskValidatorEntry entry, @Injectable ValidationConfig validationConfig) throws Exception {
        TeamTask teamTask = TeamTask.builder()
                .summary("summary")
                .description("description")
                .acceptationCriteria("")
                .estimation(null)
                .build();
        new Expectations() {{
            entry.getTeamTask();
            result = teamTask;

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7f;

            validationConfig.getCriteria().isActive();
            result = true;

            validationConfig.getEstimation().isActive();
            result = true;
        }};

        final List<Violation> violations = TeamTaskScorer.performScorer(entry.getTeamTask(), validationConfig).getViolations();
        assertThat(violations.size()).isEqualTo(6);
    }

}
