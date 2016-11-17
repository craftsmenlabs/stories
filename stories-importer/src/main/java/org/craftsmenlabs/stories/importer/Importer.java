package org.craftsmenlabs.stories.importer;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;

import java.util.List;

public interface Importer {
    List<Issue> getIssues();
}
