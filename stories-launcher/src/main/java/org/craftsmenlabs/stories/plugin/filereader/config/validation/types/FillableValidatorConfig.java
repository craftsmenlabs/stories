package org.craftsmenlabs.stories.plugin.filereader.config.validation.types;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;

import java.util.List;

@Data
public abstract class FillableValidatorConfig extends ValidatorConfig {
    private List<String> fillFields;

    public void complement(ValidationConfig.FillableValidatorEntry validatorEntry) {
        super.complement(validatorEntry);
        validatorEntry.setEnabledFields(fillFields);
    }
}
