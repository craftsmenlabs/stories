package org.craftsmenlabs.stories.plugin.filereader.config;

import org.craftsmenlabs.stories.api.models.exception.StoriesException;

public interface ValidatableConfig {
    void validate() throws StoriesException;
}
