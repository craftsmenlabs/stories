package org.craftsmenlabs.stories.importer;

import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;

import java.util.List;

public interface Importer {
    Backlog getBacklog();
}
