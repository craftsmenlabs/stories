package org.craftsmenlabs.stories.plugin.filereader.config.validation.types;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;

@Data
public abstract class ValidatorConfig {
    private float ratingThreshold;
    private boolean active;

    public void complement(ValidationConfig.ValidatorEntry validatorEntry) {
        validatorEntry.setRatingThreshold(getRatingThreshold());
        validatorEntry.setActive(isActive());
    }
}
