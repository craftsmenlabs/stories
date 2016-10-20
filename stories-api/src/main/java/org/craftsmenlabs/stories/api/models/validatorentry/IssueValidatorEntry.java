package org.craftsmenlabs.stories.api.models.validatorentry;

import java.util.List;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class IssueValidatorEntry {
    private Issue issue;
    private float pointsValuation = 0.0f;
    private List<Violation> violations;
    private Rating rating;

    private UserStoryValidatorEntry userStoryValidatorEntry;
    private AcceptanceCriteriaValidatorEntry acceptanceCriteriaValidatorEntry;
    private EstimationValidatorEntry estimationValidatorEntry;
}