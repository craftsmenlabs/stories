package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Epic;
import org.craftsmenlabs.stories.api.models.validatorentry.EpicValidatorEntry;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class EpicScorerTest {

    @Test
    public void scorerShouldFailWithoutEnabledFields() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        config.getEpic().setEnabledFields(new LinkedList<>());
        Epic testedEpic = this.getDefaultEpic();


        EpicValidatorEntry result = (EpicValidatorEntry) new FillableFieldScorer<Epic>(config).performScorer(testedEpic);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
        assertThat(result.getPointsValuation()).isEqualTo(0.0f);
    }

    @Test
    public void scorerShouldReturnFullPointsOnBugWithAllFieldsFilled() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        Epic testedEpic = this.getDefaultEpic();

        EpicValidatorEntry result = (EpicValidatorEntry) new FillableFieldScorer<Epic>(config).performScorer(testedEpic);
        assertThat(result.getRating()).isEqualTo(Rating.SUCCESS);
        assertThat(result.getPointsValuation()).isEqualTo(1f);
    }

    @Test(expected = StoriesException.class)
    public void scorerShouldThrowExceptionWhenFieldIsUnknown() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        config.getEpic().setEnabledFields(Arrays.asList("goal", "unknown_field"));
        Epic testedEpic = this.getDefaultEpic();

        new FillableFieldScorer<Epic>(config).performScorer(testedEpic);
    }

    @Test
    public void scorerShouldAddViolationAndReduceScoreOnMissingAttribute() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        Epic testedEpic = this.getDefaultEpic();
        testedEpic.setGoal("");
        EpicValidatorEntry result = (EpicValidatorEntry) new FillableFieldScorer<Epic>(config).performScorer(testedEpic);

        assertThat(result.getPointsValuation()).isEqualTo(0.0f);
        assertThat(result.getViolations().size()).isEqualTo(1);
    }

    private ValidationConfig getDefaultConfig() {
        ValidationConfig.EpicValidatorEntry config = new ValidationConfig.EpicValidatorEntry();
        config.setRatingThreshold(0.5f);
        config.setActive(true);
        config.setEnabledFields(Collections.singletonList("goal"));
        return ValidationConfig.builder()
                .epic(config)
                .build();
    }

    private Epic getDefaultEpic() {
        return Epic.builder()
                .summary("Fix stuff")
                .goal("goal")
                .key("EPIC-1")
                .build();
    }
}
