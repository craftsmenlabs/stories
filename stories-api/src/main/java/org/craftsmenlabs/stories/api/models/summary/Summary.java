package org.craftsmenlabs.stories.api.models.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Summary implements Summarizable<Summary> {

    private ScorableSummary backlog;
    private BacklogItemListSummary issues;
    private BacklogItemListSummary features;
    private BacklogItemListSummary bugs;
    private BacklogItemListSummary epics;
    private BacklogItemListSummary teamTasks;
    private BacklogItemListSummary featureUserStory;
    private BacklogItemListSummary featureCriteria;
    private BacklogItemListSummary featureEstimation;
    private Map<ViolationType, Integer> violationCounts;

    public Summary() {
        issues = new BacklogItemListSummary();
        backlog = new ScorableSummary();
        features = new BacklogItemListSummary();
        bugs = new BacklogItemListSummary();
        epics = new BacklogItemListSummary();
        teamTasks = new BacklogItemListSummary();

        featureUserStory = new BacklogItemListSummary();
        featureCriteria = new BacklogItemListSummary();
        featureEstimation = new BacklogItemListSummary();

        violationCounts = new HashMap<>();
    }

    @Override
    public Summary divideBy(int denominator) {
        if (denominator == 0) {
            return new Summary();
        } else {
            return Summary.builder()
                    .backlog(backlog.divideBy(denominator))
                    .features(features.divideBy(denominator))
                    .bugs(bugs.divideBy(denominator))
                    .epics(epics.divideBy(denominator))
                    .issues(issues.divideBy(denominator))
                    .teamTasks(teamTasks.divideBy(denominator))
                    .featureUserStory(featureUserStory.divideBy(denominator))
                    .featureCriteria(featureCriteria.divideBy(denominator))
                    .featureEstimation(featureEstimation.divideBy(denominator))
//                    .violationCounts()
                    .build();
        }
    }

    @Override
    public Summary plus(Summary that) {
        return Summary.builder()
                .backlog(backlog.plus(that.getBacklog()))
                .issues(issues.plus(that.getIssues()))
                .features(features.plus(that.getFeatures()))
                .bugs(bugs.plus(that.getBugs()))
                .epics(epics.plus(that.getEpics()))
                .teamTasks(teamTasks.plus(that.getTeamTasks()))
                .featureUserStory(featureUserStory.plus(that.getFeatureUserStory()))
                .featureCriteria(featureCriteria.plus(that.getFeatureCriteria()))
                .featureEstimation(featureEstimation.plus(that.getFeatureEstimation()))
//                .violationCounts(
//                        that.getViolationCounts().entrySet().stream()
//                                .collect(Collectors.))
                .build();
    }
}
