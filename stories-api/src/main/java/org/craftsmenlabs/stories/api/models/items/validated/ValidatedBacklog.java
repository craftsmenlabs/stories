package org.craftsmenlabs.stories.api.models.items.validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.types.AbstractValidatedItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class ValidatedBacklog extends AbstractValidatedItem<Backlog> {
    @JsonProperty("validatedItems")
    private List<? extends ValidatedBacklogItem> items = new ArrayList<>();

    @Builder
    public ValidatedBacklog(List<Violation> violations, Rating rating, Backlog backlog, List<? extends ValidatedBacklogItem> items, float scoredPercentage, float missedPercentage, float scoredPoints, float missedPoints) {
        super(violations, rating, backlog, scoredPercentage, missedPercentage, scoredPoints, missedPoints);
        this.items = items;
    }

    @JsonIgnore
    public List<ValidatedFeature> getValidatedFeatures() {
        return this.filterIssueList(ValidatedFeature.class);
    }

    @JsonIgnore
    public List<ValidatedBug> getValidatedBugs() {
        return this.filterIssueList(ValidatedBug.class);
    }


    @JsonIgnore
    public List<ValidatedEpic> getValidatedEpics() {
        return this.filterIssueList(ValidatedEpic.class);
    }

    @JsonIgnore
    public List<ValidatedTeamTask> getValidatedTeamTasks() {
        return this.filterIssueList(ValidatedTeamTask.class);
    }

    private <T extends ValidatedBacklogItem> List<T> filterIssueList(Class<T> cl) {
        return this.items.stream()
                .filter(cl::isInstance)
                .map(cl::cast)
                .collect(Collectors.toList());
    }
}
