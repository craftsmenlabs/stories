package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.validatorentry.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.craftsmenlabs.stories.api.models.Rating.FAIL;
import static org.craftsmenlabs.stories.api.models.Rating.SUCCESS;

public class SummaryBuilder {

    public Summary build(BacklogValidatorEntry entry){

        BacklogItemList<FeatureValidatorEntry> issues = entry.getFeatureValidatorEntries();
        BacklogItemList<BugValidatorEntry> bugs = entry.getBugValidatorEntries();
        BacklogItemList<EpicValidatorEntry> epics = entry.getEpicValidatorEntries();
        List<UserStoryValidatorEntry> featureStories     = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getUserStoryValidatorEntry).collect(Collectors.toList());
        List<AcceptanceCriteriaValidatorEntry> featureCriteria    = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getAcceptanceCriteriaValidatorEntry).collect(Collectors.toList());
        List<EstimationValidatorEntry> featureEstimations = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getEstimationValidatorEntry).collect(Collectors.toList());

        return Summary.builder()
                .backlog(Summary.ScorableSummary.builder()
                        .pointsValuation(entry.getPointsValuation())
                        .rating(entry.getRating())
                        .build())
                .features(getCount(issues.getItems()))
                .bugs(getCount(bugs.getItems()))
                .epics(getCount(epics.getItems()))
                .featureUserStory(getCount(featureStories))
                .featureCriteria(getCount(featureCriteria))
                .featureEstimation(getCount(featureEstimations))
                .build();
    }

    public static Summary.BacklogItemListSummary getCount(List<? extends AbstractScorable> list) {
        if(list == null) return zero();

        return Summary.BacklogItemListSummary.builder()
                .failed(list.stream().filter(o -> o.getRating() == FAIL).count())
                .passed(list.stream().filter(o -> o.getRating() == SUCCESS).count())
                .count((long) list.size())
                .build();
    }

    public static Summary.BacklogItemListSummary zero(){
        return Summary.BacklogItemListSummary.builder()
                .failed(0)
                .passed(0)
                .count(0)
                .build();
    }

    public static Summary.BacklogItemListSummary divide(Summary.BacklogItemListSummary summary, int denominator){
        return Summary.BacklogItemListSummary.builder()
                .failed(summary.getFailed() / denominator)
                .passed(summary.getPassed() / denominator)
                .count( summary.getCount() / denominator)
                .build();
    }


}
