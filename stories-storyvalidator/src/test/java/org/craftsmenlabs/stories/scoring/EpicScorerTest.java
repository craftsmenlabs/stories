package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEpic;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class EpicScorerTest {
    private EpicScorer getScorer(ValidationConfig validationConfig) {
        return new EpicScorer(validationConfig);
    }

    @Test
    public void scorerShouldFailWithoutEnabledFields() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        config.getEpic().setEnabledFields(new LinkedList<>());
        Epic testedEpic = this.getDefaultEpic();

        ValidatedEpic result = getScorer(config).validate(testedEpic);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
        assertThat(result.getPointsValuation()).isEqualTo(0.0f);
    }

    @Test
    public void scorerShouldReturnFullPointsOnBugWithAllFieldsFilled() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        Epic testedEpic = this.getDefaultEpic();

        ValidatedEpic result = getScorer(config).validate(testedEpic);
        assertThat(result.getRating()).isEqualTo(Rating.SUCCESS);
        assertThat(result.getPointsValuation()).isEqualTo(1f);
    }

    @Test(expected = StoriesException.class)
    public void scorerShouldThrowExceptionWhenFieldIsUnknown() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        config.getEpic().setEnabledFields(Arrays.asList("goal", "unknown_field"));
        Epic testedEpic = this.getDefaultEpic();

        getScorer(config).validate(testedEpic);
    }

    @Test
    public void scorerShouldAddViolationAndReduceScoreOnMissingAttribute() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        Epic testedEpic = this.getDefaultEpic();
        testedEpic.setGoal("");
        ValidatedEpic result = getScorer(config).validate(testedEpic);

        assertThat(result.getPointsValuation()).isEqualTo(0.0f);
        assertThat(result.getViolations().size()).isEqualTo(1);
    }

    private ValidationConfig getDefaultConfig() {
        ValidationConfig.EpicValidatorEntry config = new ValidationConfig.EpicValidatorEntry();
        config.setRatingThreshold(0.5f);
        config.setActive(true);
        config.setEnabledFields(Arrays.asList("goal"));
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
