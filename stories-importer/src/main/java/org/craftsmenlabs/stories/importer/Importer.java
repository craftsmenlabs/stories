package org.craftsmenlabs.stories.importer;

import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;

public interface Importer {
    Backlog getBacklog(StorynatorConfig storynatorConfig);
}
