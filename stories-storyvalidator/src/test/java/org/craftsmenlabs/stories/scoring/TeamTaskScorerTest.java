package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.TeamTask;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedTeamTask;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class
TeamTaskScorerTest {
    private TeamTaskScorer getScorer(ValidationConfig validationConfig) {
        return new TeamTaskScorer(validationConfig);
    }

    @Test
    public void testPerformScorerReturnsZeroOnEmpty(@Injectable ValidatedTeamTask entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem();
            result = new TeamTask();

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7;
        }};

        final ValidatedTeamTask validate = getScorer(validationConfig).validate(entry.getItem());
        double score = validate.getScoredPoints();
        assertThat(score).isEqualTo(0.0);
        assertThat(validate.getAllViolations()).hasSize(8);
    }

    @Test
    @Ignore
    public void testPerformScorerReturnsNullOnEmpty(@Injectable ValidatedTeamTask entry, @Injectable ValidationConfig validationConfig) throws Exception {
        new Expectations() {{
            entry.getItem();
            result = null;

            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7;
        }};

        final ValidatedTeamTask validate = getScorer(validationConfig).validate(entry.getItem());
        double score = validate.getScoredPoints();
        assertThat(score).isEqualTo(0.0);
    }

    @Test
    public void testPerformScorerReturnsOneOnPerfect(@Injectable ValidatedTeamTask entry, @Injectable ValidationConfig validationConfig) throws Exception {
        TeamTask teamTask = TeamTask.builder()
                .summary("summary")
                .description("description")
                .acceptationCriteria(StringUtils.repeat("Given when then ", 20))
                .estimation(1.)
                .build();
        new Expectations() {{
            entry.getItem();
            result = teamTask;

            validationConfig.getCriteria().getGivenKeywords();
            result = Arrays.asList("given ");
            validationConfig.getCriteria().getWhenKeywords();
            result = Arrays.asList("when ");

            validationConfig.getCriteria().getThenKeywords();
            result = Arrays.asList("then ");


            validationConfig.getTeamTask().getRatingThreshold();
            result = 0.7;
        }};

        final ValidatedTeamTask validate = getScorer(validationConfig).validate(entry.getItem());
        double score = validate.getScoredPoints();
        assertThat(score).isEqualTo(1.0);
        assertThat(validate.getAllViolations()).hasSize(0);
    }
}
