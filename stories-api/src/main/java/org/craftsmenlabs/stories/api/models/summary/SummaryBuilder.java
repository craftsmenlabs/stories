package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.validatorentry.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.craftsmenlabs.stories.api.models.Rating.FAIL;
import static org.craftsmenlabs.stories.api.models.Rating.SUCCESS;

public class SummaryBuilder {

    public Summary build(BacklogValidatorEntry entry){
        List<FeatureValidatorEntry> issues = entry.getFeatureValidatorEntries().getItems();
        List<BugValidatorEntry> bugs = entry.getBugValidatorEntries().getItems();
        List<EpicValidatorEntry> epics = entry.getEpicValidatorEntries().getItems();
        List<TeamTaskValidatorEntry> teamTasks = entry.getTeamTaskValidatorEntries().getItems();
        List<UserStoryValidatorEntry> featureStories     = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getUserStoryValidatorEntry).collect(Collectors.toList());
        List<AcceptanceCriteriaValidatorEntry> featureCriteria    = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getAcceptanceCriteriaValidatorEntry).collect(Collectors.toList());
        List<EstimationValidatorEntry> featureEstimations = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getEstimationValidatorEntry).collect(Collectors.toList());

        return Summary.builder()
                .backlog(ScorableSummary.builder()
                        .pointsValuation(entry.getPointsValuation())
                        .rating(entry.getRating())
                        .build())
                .features(getCount(issues))
                .bugs(getCount(bugs))
                .epics(getCount(epics))
                .teamTask(getCount(teamTasks))
                .featureUserStory(getCount(featureStories))
                .featureCriteria(getCount(featureCriteria))
                .featureEstimation(getCount(featureEstimations))
                .build();
    }

    public BacklogItemListSummary getCount(List<? extends AbstractScorable> list) {
        if(list == null) return new BacklogItemListSummary();

        return BacklogItemListSummary.builder()
                .failed(list.stream().filter(o -> o.getRating() == FAIL).count())
                .passed(list.stream().filter(o -> o.getRating() == SUCCESS).count())
                .count((long) list.size())
                .build();
    }
}
