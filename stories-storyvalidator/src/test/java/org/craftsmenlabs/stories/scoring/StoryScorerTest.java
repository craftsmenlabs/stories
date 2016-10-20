package org.craftsmenlabs.stories.scoring;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.junit.Test;
import mockit.*;

public class StoryScorerTest {

    @Tested
    private StoryScorer storyScorer;

    @Test
    public void testPerformScorer(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = "As a super office user \n"
                + "I would like to be informed about the alarms in my user \\n\"\n"
                + "so I can have the most preferred alarm on top.";

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = storyScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(1.0f);
    }

    @Test
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        List<Violation> v = new ArrayList<>();
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = "";

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = storyScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }

    @Test
    public void testPerformScorer_ReturnsNullOnEmpty(@Injectable IssueValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        new Expectations() {{
            entry.getIssue().getUserstory();
            result = null;

            validationConfig.getStory().getRatingtreshold();
            result = 0.7f;
        }};

        float score = storyScorer.performScorer(entry.getIssue().getUserstory(), validationConfig).getPointsValuation();
        assertThat(score).isEqualTo(0.0f);
    }
}
