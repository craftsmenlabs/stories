package org.craftsmenlabs.stories.isolator.parser;

import java.util.List;

import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;

public interface Parser {
    List<Feature> getIssues(String input);
}
