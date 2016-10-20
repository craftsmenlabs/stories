package org.craftsmenlabs.stories.reporter;

import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;

public interface Reporter {
    void report(BacklogValidatorEntry backlogValidatorEntry);
}
