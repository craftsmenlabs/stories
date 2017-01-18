package org.craftsmenlabs.stories.importer.jira;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JiraRequest {
    private String jql;
    private int maxResults;
}
