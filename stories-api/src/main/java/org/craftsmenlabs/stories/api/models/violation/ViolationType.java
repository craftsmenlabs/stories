package org.craftsmenlabs.stories.api.models.violation;

public enum ViolationType
{
	BacklogEmptyViolation,
	BacklogRatingViolation,
	StoryEmptyViolation,
	StoryMultipleLinesClauseViolation,
	StoryLengthClauseViolation,
	StoryAsIsClauseViolation,
	StoryIClauseViolation,
	StorySoClauseViolation,
	StoryRatingViolation,
    CriteriaVoidViolation,
    CriteriaLengthViolation,
    CriteriaGivenClauseViolation,
	CriteriaWhenClauseViolation,
	CriteriaThenClauseViolation,
	CriteriaRatingViolation,
    BugFieldEmptyViolation,
    TeamTaskSummaryEmptyViolation,
    TeamTaskDescriptionEmptyViolation,
    TeamTaskEstimationEmptyViolation,
    NoFillableFieldsViolation
}
