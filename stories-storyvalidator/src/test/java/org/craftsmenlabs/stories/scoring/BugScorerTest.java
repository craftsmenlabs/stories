package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBug;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class BugScorerTest {
    private BugScorer getScorer(ValidationConfig config) {
        return new BugScorer(config);
    }

    @Test
    public void scorerShouldFailWithoutEnabledFields() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        config.getBug().setEnabledFields(new LinkedList<>());
        Bug testedBug = this.getDefaultBug();

        ValidatedBug result = getScorer(config).validate(testedBug);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
        assertThat(result.getPointsValuation()).isEqualTo(0.0f);
    }

    @Test
    public void scorerShouldReturnFullPointsOnBugWithAllFieldsFilled() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        Bug testedBug = this.getDefaultBug();

        ValidatedBug result = getScorer(config).validate(testedBug);
        assertThat(result.getRating()).isEqualTo(Rating.SUCCESS);
        assertThat(result.getPointsValuation()).isEqualTo(1f);
    }

    @Test(expected = StoriesException.class)
    public void scorerShouldThrowExceptionWhenFieldIsUnknown() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        config.getBug().setEnabledFields(Arrays.asList("priority", "unknown_field"));
        Bug testedBug = this.getDefaultBug();

        getScorer(config).validate(testedBug);
    }

    @Test
    public void scorerShouldAddViolationAndReduceScoreOnMissingAttribute() throws Exception {
        ValidationConfig config = this.getDefaultConfig();
        Bug testedBug = this.getDefaultBug();
        testedBug.setReproductionPath("");
        ValidatedBug result = getScorer(config).validate(testedBug);

        assertThat(result.getPointsValuation()).isEqualTo(0.8f);
        assertThat(result.getViolations().size()).isEqualTo(1);
    }

    private ValidationConfig getDefaultConfig() {
        ValidationConfig.BugValidatorEntry config = new ValidationConfig.BugValidatorEntry();
        config.setRatingThreshold(0.5f);
        config.setActive(true);
        config.setEnabledFields(Arrays.asList("expected", "priority", "software", "reproduction", "acceptation"));
        return ValidationConfig.builder()
                .bug(config)
                .build();
    }

    private Bug getDefaultBug() {
        return Bug.builder()
                .acceptationCriteria("The box should not catch input when disabled")
                .expectedBehavior("The box should ignore input when disabled")
                .priority("Blocking")
                .reproductionPath("I have no idea what I did, but I filled this in anyway")
                .summary("Input still caught when box is disabled")
                .software("Windows ME with Internet Explorer 5")
                .build();
    }
}
