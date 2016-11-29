package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.Rating;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SummaryTest {
    @Test
    public void divideBy() throws Exception {
        Summary s = Summary.builder()
            .backlog(ScorableSummary.builder().pointsValuation(1f).rating(Rating.FAIL).violationCount(10).build())
            .features(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
            .bugs(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
            .epics(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
            .featureUserStory(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
            .featureCriteria(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
            .featureEstimation(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
                .build();
        Summary result = Summary.builder()
            .backlog(ScorableSummary.builder().pointsValuation(0.1f).rating(null).violationCount(1).build())
            .features(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
            .bugs(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
            .epics(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
            .featureUserStory(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
            .featureCriteria(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
            .featureEstimation(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
                .build();

        assertThat(s.divideBy(10)).isEqualTo(result);
    }

    @Test
    public void plus() throws Exception {
        Summary a = Summary.builder()
                .backlog(ScorableSummary.builder().pointsValuation(1f).rating(Rating.FAIL).violationCount(10).build())
                .features(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
                .bugs(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
                .epics(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
                .featureUserStory(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
                .featureCriteria(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
                .featureEstimation(BacklogItemListSummary.builder().passed(50).failed(70).count(120).build())
                .build();
        Summary b = Summary.builder()
                .backlog(ScorableSummary.builder().pointsValuation(2f).rating(Rating.SUCCESS).violationCount(1).build())
                .features(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
                .bugs(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
                .epics(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
                .featureUserStory(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
                .featureCriteria(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
                .featureEstimation(BacklogItemListSummary.builder().passed(5).failed(7).count(12).build())
                .build();
        Summary c = Summary.builder()
                .backlog(ScorableSummary.builder().pointsValuation(3f).rating(null).violationCount(11).build())
                .features(BacklogItemListSummary.builder().passed(55).failed(77).count(132).build())
                .bugs(BacklogItemListSummary.builder().passed(55).failed(77).count(132).build())
                .epics(BacklogItemListSummary.builder().passed(55).failed(77).count(132).build())
                .featureUserStory(BacklogItemListSummary.builder().passed(55).failed(77).count(132).build())
                .featureCriteria(BacklogItemListSummary.builder().passed(55).failed(77).count(132).build())
                .featureEstimation(BacklogItemListSummary.builder().passed(55).failed(77).count(132).build())
                .build();
        assertThat(a.plus(b)).isEqualTo(c);
    }

}