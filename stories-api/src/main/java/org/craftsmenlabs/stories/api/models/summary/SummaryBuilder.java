package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.items.validated.*;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.craftsmenlabs.stories.api.models.Rating.FAIL;
import static org.craftsmenlabs.stories.api.models.Rating.SUCCESS;

public class SummaryBuilder {

    public Summary build(ValidatedBacklog entry) {
        List<ValidatedFeature> issues = entry.getValidatedFeatures();
        List<ValidatedBug> bugs = entry.getValidatedBugs();
        List<ValidatedEpic> epics = entry.getValidatedEpics();
        List<ValidatedTeamTask> teamTasks = entry.getValidatedTeamTasks();
        List<ValidatedUserStory> featureStories = entry.getValidatedFeatures().stream().map(ValidatedFeature::getValidatedUserStory).collect(Collectors.toList());
        List<ValidatedAcceptanceCriteria> featureCriteria = entry.getValidatedFeatures().stream().map(ValidatedFeature::getValidatedAcceptanceCriteria).collect(Collectors.toList());
        List<ValidatedEstimation> featureEstimations = entry.getValidatedFeatures().stream().map(ValidatedFeature::getValidatedEstimation).collect(Collectors.toList());

        List<? extends ValidatedBacklogItem> allEntries = entry.getItems();

        return Summary.builder()
                .backlog(ScorableSummary.builder()
                        .pointsValuation(entry.getPointsValuation())
                        .rating(entry.getRating())
                        .build())
                .issues(getCount(allEntries))
                .features(getCount(issues))
                .bugs(getCount(bugs))
                .epics(getCount(epics))
                .teamTasks(getCount(teamTasks))
                .featureUserStory(getCount(featureStories))
                .featureCriteria(getCount(featureCriteria))
                .featureEstimation(getCount(featureEstimations))
                .violationCounts(getViolationCount(allEntries))
                .build();
    }

    public BacklogItemListSummary getCount(List<? extends AbstractValidatedItem> list) {
        if (list == null) return new BacklogItemListSummary();

        return BacklogItemListSummary.builder()
                .failed(list.stream().filter(o -> o.getRating() == FAIL).count())
                .passed(list.stream().filter(o -> o.getRating() == SUCCESS).count())
                .count((long) list.size())
                .build();
    }

    public Map<ViolationType, Integer> getViolationCount(List<? extends ValidatedBacklogItem> list) {
        return list.stream()
                .map(ValidatedBacklogItem::getViolations)
                .flatMap(Collection::stream)
                // Leave this Violation cast. It won't work otherwise
                .map(v -> ((Violation) v).getViolationType())
                .collect(Collectors.groupingBy(
                        o -> o,
                        Collectors.reducing(
                                0,
                                u -> 1,
                                (u, u2) -> u + u2)));
    }
}
