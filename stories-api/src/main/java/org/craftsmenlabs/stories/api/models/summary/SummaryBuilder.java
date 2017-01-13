package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.items.types.AbstractScorable;
import org.craftsmenlabs.stories.api.models.items.validated.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.craftsmenlabs.stories.api.models.Rating.FAIL;
import static org.craftsmenlabs.stories.api.models.Rating.SUCCESS;

public class SummaryBuilder {

    public Summary build(BacklogValidatorEntry entry){
        List<ValidatedFeature> issues = entry.getFeatureValidatorEntries().getItems();
        List<ValidatedBug> bugs = entry.getBugValidatorEntries().getItems();
        List<ValidatedEpic> epics = entry.getEpicValidatorEntries().getItems();
        List<ValidatedTeamTask> teamTasks = entry.getTeamTaskValidatorEntries().getItems();
        List<ValidatedUserStory> featureStories = entry.getFeatureValidatorEntries().getItems().stream().map(ValidatedFeature::getValidatedUserStory).collect(Collectors.toList());
        List<ValidatedAcceptanceCriteria> featureCriteria = entry.getFeatureValidatorEntries().getItems().stream().map(ValidatedFeature::getValidatedAcceptanceCriteria).collect(Collectors.toList());
        List<ValidatedEstimation> featureEstimations = entry.getFeatureValidatorEntries().getItems().stream().map(ValidatedFeature::getValidatedEstimation).collect(Collectors.toList());

        return Summary.builder()
                .backlog(ScorableSummary.builder()
                        .pointsValuation(entry.getPointsValuation())
                        .rating(entry.getRating())
                        .build())
                .features(getCount(issues))
                .bugs(getCount(bugs))
                .epics(getCount(epics))
                .teamTasks(getCount(teamTasks))
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
