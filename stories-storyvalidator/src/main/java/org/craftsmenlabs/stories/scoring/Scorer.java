package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.ScrumItem;
import org.craftsmenlabs.stories.api.models.validatorentry.Scorable;

public interface Scorer<T extends ScrumItem >{
    Scorable performScoring(T items, ValidationConfig validationConfig);
}
