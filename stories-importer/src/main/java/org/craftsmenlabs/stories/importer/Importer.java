package org.craftsmenlabs.stories.importer;

import java.util.Optional;

public interface Importer {
    Optional<String> getDataAsString();
}
