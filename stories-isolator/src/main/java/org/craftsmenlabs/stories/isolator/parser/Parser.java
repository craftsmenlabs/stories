package org.craftsmenlabs.stories.isolator.parser;

import java.util.List;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;

public interface Parser {
    List<Issue> getIssues(String input);
}
