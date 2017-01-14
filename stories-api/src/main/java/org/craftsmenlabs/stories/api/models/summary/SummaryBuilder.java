package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.validated.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.craftsmenlabs.stories.api.models.Rating.FAIL;
import static org.craftsmenlabs.stories.api.models.Rating.SUCCESS;

public class SummaryBuilder {

    public Summary build(ValidatedBacklog entry) {
        List<ValidatedFeature> issues = entry.getValidatedFeatures();
        List<ValidatedBug> bugs = entry.getValidatedBugs();
        List<ValidatedEpic> epics = entry.getValidatedEpics();
        List<ValidatedTeamTask> teamTasks = entry.getValidatedTeamTasks();
        List<ValidatedUserStory> featureStories = issues.stream().map(ValidatedFeature::getValidatedUserStory).collect(Collectors.toList());
        List<ValidatedAcceptanceCriteria> featureCriteria = issues.stream().map(ValidatedFeature::getValidatedAcceptanceCriteria).collect(Collectors.toList());
        List<ValidatedEstimation> featureEstimations = issues.stream().map(ValidatedFeature::getValidatedEstimation).collect(Collectors.toList());

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

    public BacklogItemListSummary getCount(List<? extends AbstractValidatedItem> list) {
        if(list == null) return new BacklogItemListSummary();

        return BacklogItemListSummary.builder()
                .failed(list.stream().filter(o -> o.getRating() == FAIL).count())
                .passed(list.stream().filter(o -> o.getRating() == SUCCESS).count())
                .count((long) list.size())
                .build();
    }
}
