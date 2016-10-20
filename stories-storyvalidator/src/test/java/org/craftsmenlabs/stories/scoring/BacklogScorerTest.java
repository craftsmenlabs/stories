package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class BacklogScorerTest {

    @Tested
    BacklogScorer backlogScorer;

//    @Test
//    public void testPerformScorer(@Injectable BacklogValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
//        List<IssueValidatorEntry> issues = Arrays.asList(
//                IssueValidatorEntry.builder()
//
//        );
//        new Expectations() {{
//            entry.getBacklog().getIssues();
//            result =
//
//
//            validationConfig.getCriteria().getRatingtreshold();
//            result = 0.7f;
//        }};
//
////        float score = backlogScorer.performScorer(entry.getIssue().getAcceptanceCriteria(), validationConfig).getPointsValuation();
////        assertThat(score).isCloseTo(1.0f, withinPercentage(5));
//    }



    @Test
    public void testPerformScorer_ReturnsZeroOnEmpty(@Injectable BacklogValidatorEntry entry, @Injectable ScorerConfigCopy validationConfig) throws Exception {
        float score = backlogScorer.performScorer(null, null, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }


    @Test
    public void testGetRating_ReturnsFailOnNull(@Injectable ScorerConfigCopy validationConfig ){
        assertThat(Rating.FAIL).isEqualTo(backlogScorer.getRating(validationConfig, null));

    }

}
