package org.craftsmenlabs.stories.plugin.filereader.config.validation;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.validation.types.FillableValidatorConfig;

@Data
public class BugValidatorConfig extends FillableValidatorConfig {
    public ValidationConfig.BugValidatorEntry convert() {
        ValidationConfig.BugValidatorEntry bug = new ValidationConfig.BugValidatorEntry();
        super.complement(bug);
        return bug;
    }
}
