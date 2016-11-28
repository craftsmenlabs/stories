package org.craftsmenlabs.stories.api.models.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summary {

    private ScorableSummary backlog;
    private BacklogItemListSummary features;
    private BacklogItemListSummary bugs;
    private BacklogItemListSummary epics;

    private BacklogItemListSummary featureUserStory;
    private BacklogItemListSummary featureCriteria;
    private BacklogItemListSummary featureEstimation;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScorableSummary{
        private float pointsValuation;
        private Rating rating;
        private long violationCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BacklogItemListSummary {
        private long passed;
        private long failed;
        private long count;
    }
}
