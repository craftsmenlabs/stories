package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;

public interface Ranking {
    float createRanking(BacklogValidatorEntry backlogValidatorEntry);
}
