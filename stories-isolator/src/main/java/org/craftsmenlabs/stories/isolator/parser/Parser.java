package org.craftsmenlabs.stories.isolator.parser;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;

import java.util.List;

public interface Parser {
    List<Issue> getIssues(String input);
}
