package org.craftsmenlabs.stories.ranking;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class CurvedRankingTest2 {
    @Tested
    CurvedRanking curvedRanking;

    @Test
    public void createRankingReturnsZeroOnNull(){
        assertThat(curvedRanking.createRanking(null)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Test
    public void createRankingReturnsZeroOnNullBacklogEntries(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = null;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Test
    public void createRankingReturnsZeroOnEmptyBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = new ArrayList<>();
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0f, withinPercentage(1.0));
    }

    @Test
    public void createRankingReturnsZeroOnZeroScoreBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        List<IssueValidatorEntry> issues = Arrays.asList(
                IssueValidatorEntry.builder().pointsValuation(0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0f).build()
        );

        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0f, withinPercentage(1.0));
    }
    @Test
    public void createRankingReturnsOneOnPerfectBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        List<IssueValidatorEntry> issues = Arrays.asList(
                IssueValidatorEntry.builder().pointsValuation(1f).build(),
                IssueValidatorEntry.builder().pointsValuation(1f).build(),
                IssueValidatorEntry.builder().pointsValuation(1f).build(),
                IssueValidatorEntry.builder().pointsValuation(1f).build(),
                IssueValidatorEntry.builder().pointsValuation(1f).build()
        );

        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(1f, withinPercentage(1.0));
    }

    @Test
    public void createRankingReturnsScoreOnGoodGradientMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        List<IssueValidatorEntry> issues = Arrays.asList(
                IssueValidatorEntry.builder().pointsValuation(1f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.8f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.6f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.4f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.2f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.0f).build()
        );

        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0.608f, withinPercentage(1.0));
    }


    @Test
    public void createRankingReturnsScoreOnBadGradientMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        List<IssueValidatorEntry> issues = Arrays.asList(
                IssueValidatorEntry.builder().pointsValuation(0.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.2f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.4f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.6f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.8f).build(),
                IssueValidatorEntry.builder().pointsValuation(1f).build()
                );

        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isCloseTo(0.391f, withinPercentage(1.0));
    }

    @Test
    public void createRankingReturnsHighScoreOnGoodMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        List<IssueValidatorEntry> issues = Arrays.asList(
                IssueValidatorEntry.builder().pointsValuation(1.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(1.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(1.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.0f).build()
                );

        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isGreaterThanOrEqualTo(0.5f);
    }

    @Test
    public void createRankingReturnsLowScoreOnBadMixedBacklog(@Injectable BacklogValidatorEntry backlogValidatorEntry){
        List<IssueValidatorEntry> issues = Arrays.asList(
                IssueValidatorEntry.builder().pointsValuation(0.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(0.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(1.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(1.0f).build(),
                IssueValidatorEntry.builder().pointsValuation(1.0f).build()
                );

        new Expectations(){{
            backlogValidatorEntry.getIssueValidatorEntries();
            result = issues;
        }};

        assertThat(curvedRanking.createRanking(backlogValidatorEntry)).isLessThanOrEqualTo(0.5f);
    }
}
