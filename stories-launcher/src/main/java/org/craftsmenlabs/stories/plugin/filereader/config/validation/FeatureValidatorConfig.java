package org.craftsmenlabs.stories.plugin.filereader.config.validation;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.validation.types.ValidatorConfig;

@Data
public class FeatureValidatorConfig extends ValidatorConfig {
    public ValidationConfig.ValidatorEntry convert() {
        ValidationConfig.StoryValidatorEntry sve = new ValidationConfig.StoryValidatorEntry();
        super.complement(sve);
        return sve;
    }
}
