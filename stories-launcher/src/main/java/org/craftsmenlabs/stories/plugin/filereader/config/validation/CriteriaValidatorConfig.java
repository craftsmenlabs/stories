package org.craftsmenlabs.stories.plugin.filereader.config.validation;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.validation.types.ValidatorConfig;

import java.util.List;

@Data
public class CriteriaValidatorConfig extends ValidatorConfig {
    private List<String> givenKeywords;
    private List<String> whenKeywords;
    private List<String> thenKeywords;

    public ValidationConfig.CriteriaValidatorEntry convert() {
        ValidationConfig.CriteriaValidatorEntry sve = new ValidationConfig.CriteriaValidatorEntry();
        super.complement(sve);

        sve.setGivenKeywords(getGivenKeywords());
        sve.setWhenKeywords(getWhenKeywords());
        sve.setThenKeywords(getThenKeywords());

        return sve;
    }
}
