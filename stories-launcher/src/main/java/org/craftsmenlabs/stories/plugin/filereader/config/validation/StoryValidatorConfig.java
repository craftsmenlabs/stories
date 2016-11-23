package org.craftsmenlabs.stories.plugin.filereader.config.validation;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.validation.types.ValidatorConfig;

import java.util.List;

@Data
public class StoryValidatorConfig extends ValidatorConfig {
    private List<String> asKeywords;
    private List<String> iKeywords;
    private List<String> soKeywords;

    public ValidationConfig.StoryValidatorEntry convert() {
        ValidationConfig.StoryValidatorEntry sve = new ValidationConfig.StoryValidatorEntry();
        super.complement(sve);

        sve.setAsKeywords(getAsKeywords());
        sve.setIKeywords(getIKeywords());
        sve.setSoKeywords(getSoKeywords());

        return sve;
    }
}
