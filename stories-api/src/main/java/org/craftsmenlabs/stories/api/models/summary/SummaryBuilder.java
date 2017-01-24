package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.validatorentry.*;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.craftsmenlabs.stories.api.models.Rating.FAIL;
import static org.craftsmenlabs.stories.api.models.Rating.SUCCESS;

public class SummaryBuilder {

    public Summary build(BacklogValidatorEntry entry) {
        List<FeatureValidatorEntry> issues = entry.getFeatureValidatorEntries().getItems();
        List<BugValidatorEntry> bugs = entry.getBugValidatorEntries().getItems();
        List<EpicValidatorEntry> epics = entry.getEpicValidatorEntries().getItems();
        List<TeamTaskValidatorEntry> teamTasks = entry.getTeamTaskValidatorEntries().getItems();
        List<UserStoryValidatorEntry> featureStories = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getUserStoryValidatorEntry).collect(Collectors.toList());
        List<AcceptanceCriteriaValidatorEntry> featureCriteria = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getAcceptanceCriteriaValidatorEntry).collect(Collectors.toList());
        List<EstimationValidatorEntry> featureEstimations = entry.getFeatureValidatorEntries().getItems().stream().map(FeatureValidatorEntry::getEstimationValidatorEntry).collect(Collectors.toList());

        List<? extends AbstractScorable> allEntries = entry.getAllValidatorEntries();

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

    public BacklogItemListSummary getCount(List<? extends AbstractScorable> list) {
        if (list == null) return new BacklogItemListSummary();

        return BacklogItemListSummary.builder()
                .failed(list.stream().filter(o -> o.getRating() == FAIL).count())
                .passed(list.stream().filter(o -> o.getRating() == SUCCESS).count())
                .count((long) list.size())
                .build();
    }

    public Map<ViolationType, Integer> getViolationCount(List<? extends AbstractScorable> list){
        return list.stream()
                .flatMap(o -> o.getViolations().stream())
                .map(Violation::getViolationType)
                .collect(Collectors.groupingBy(
                        o -> o,
                        Collectors.reducing(
                                0,
                                u -> 1,
                                (u, u2) -> u + u2)));
    }
}
