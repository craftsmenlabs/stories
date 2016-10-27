package org.craftsmenlabs.stories.reporter.summary;

import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class Summary {
    private LocalDateTime dateTime;
    private Timestamp timestamp;

    private float pointsValuation;
    private Rating rating;

    private long issueCount;
    private long failedIssueCount;
    private long passedIssueCount;
    private long totalIssueViolationsCount;

    private long storyCount;
    private long failedStoryCount;
    private long passedStoryCount;
    private long totalStoryViolationsCount;

    private long criteriaCount;
    private long failedCriteriaCount;
    private long passedCriteriaCount;
    private long totalCriteriaViolationsCount;

    @Override
    public String toString() {
        return "Summary{" +
                "issueCount=" + issueCount +
                ", failedIssueCount=" + failedIssueCount +
                ", passedIssueCount=" + passedIssueCount +
                '}';
    }
}
