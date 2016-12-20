package org.craftsmenlabs.stories.api.models.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Summary implements Summarizable<Summary>{

    private ScorableSummary backlog;
    private BacklogItemListSummary features;
    private BacklogItemListSummary bugs;
    private BacklogItemListSummary epics;
    private BacklogItemListSummary teamTask;
    private BacklogItemListSummary featureUserStory;
    private BacklogItemListSummary featureCriteria;
    private BacklogItemListSummary featureEstimation;

    public Summary() {
        backlog = new ScorableSummary();
        features = new BacklogItemListSummary();
        bugs = new BacklogItemListSummary();
        epics = new BacklogItemListSummary();
        teamTask = new BacklogItemListSummary();

        featureUserStory = new BacklogItemListSummary();
        featureCriteria = new BacklogItemListSummary();
        featureEstimation = new BacklogItemListSummary();
    }

    @Override
    public Summary divideBy(int denominator) {
        if(denominator == 0){
            return new Summary();
        }else{
            return Summary.builder()
                    .backlog( backlog.divideBy(denominator) )
                    .features( features.divideBy(denominator) )
                    .bugs( bugs.divideBy(denominator) )
                    .epics( epics.divideBy(denominator) )
                    .teamTask( teamTask.divideBy(denominator))
                    .featureUserStory( featureUserStory.divideBy(denominator) )
                    .featureCriteria( featureCriteria.divideBy(denominator) )
                    .featureEstimation( featureEstimation.divideBy(denominator) )
                    .build();

        }
    }

    @Override
    public Summary plus(Summary that) {
        return Summary.builder()
            .backlog( backlog.plus(that.getBacklog()) )
            .features( features.plus(that.getFeatures()) )
            .bugs( bugs.plus(that.getBugs()) )
            .epics( epics.plus(that.getEpics()) )
            .teamTask( teamTask.plus(that.getTeamTask()) )
            .featureUserStory( featureUserStory.plus(that.getFeatureUserStory()) )
            .featureCriteria( featureCriteria.plus(that.getFeatureCriteria()) )
            .featureEstimation( featureEstimation.plus(that.getFeatureEstimation()) )
            .build();
    }
}
