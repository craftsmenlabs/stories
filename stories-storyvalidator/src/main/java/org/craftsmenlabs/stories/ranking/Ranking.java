package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.validatorentry.AbstractValidatorEntry;

import java.util.List;

public interface Ranking {
    float createRanking(List<AbstractValidatorEntry> entries);
}
