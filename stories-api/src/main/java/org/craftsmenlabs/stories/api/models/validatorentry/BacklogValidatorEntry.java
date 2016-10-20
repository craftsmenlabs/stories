package org.craftsmenlabs.stories.api.models.validatorentry;

import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Violation;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;

import java.util.List;

@Data
@Builder
public class BacklogValidatorEntry {
    private Backlog backlog;
    private List<IssueValidatorEntry> issueValidatorEntries;

    private float pointsValuation = 0.0f;
    private List<Violation> violations;
    private Rating rating;
}
