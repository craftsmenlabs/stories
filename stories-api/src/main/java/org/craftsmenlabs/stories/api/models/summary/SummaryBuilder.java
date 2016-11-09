package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;

import java.util.List;

public class SummaryBuilder {

    public Summary build(BacklogValidatorEntry entry){

        List<IssueValidatorEntry> issues = entry.getIssueValidatorEntries();

        return Summary.builder()
                .pointsValuation(entry.getPointsValuation())
                .rating(entry.getRating())
                .issueCount(issues.size())
                .failedIssueCount(issues.stream().filter(issueValidatorEntry -> issueValidatorEntry.getRating() == Rating.FAIL).count())
                .passedIssueCount(issues.stream().filter(issueValidatorEntry -> issueValidatorEntry.getRating() == Rating.SUCCESS).count())
                .totalIssueViolationsCount(issues.stream().mapToInt(issue -> issue.getViolations().size()).sum())
                
                .storyCount(issues.stream().map(IssueValidatorEntry::getUserStoryValidatorEntry).filter(story -> story.getUserStory() != null && !story.getUserStory().isEmpty()).count())
                .failedStoryCount(issues.stream().map(IssueValidatorEntry::getUserStoryValidatorEntry).filter(storyValidatorEntry -> storyValidatorEntry.getRating() == Rating.FAIL).count())
                .passedStoryCount(issues.stream().map(IssueValidatorEntry::getUserStoryValidatorEntry).filter(storyValidatorEntry -> storyValidatorEntry.getRating() == Rating.SUCCESS).count())
                .totalStoryViolationsCount(issues.stream()
                        .map(IssueValidatorEntry::getUserStoryValidatorEntry)
                        .mapToInt(Story -> Story.getViolations().size())
                        .sum())
                
                .criteriaCount(issues.stream().map(IssueValidatorEntry::getAcceptanceCriteriaValidatorEntry).count())
                .failedCriteriaCount(issues.stream().map(IssueValidatorEntry::getAcceptanceCriteriaValidatorEntry).filter(criteria -> criteria.getRating()== Rating.FAIL).count())
                .passedCriteriaCount(issues.stream().map(IssueValidatorEntry::getAcceptanceCriteriaValidatorEntry).filter(criteria -> criteria.getRating() == Rating.SUCCESS).count())
                .totalCriteriaViolationsCount(issues.stream().mapToInt(issue-> issue.getAcceptanceCriteriaValidatorEntry().getViolations().size()).sum())
                .build();
    }
}
