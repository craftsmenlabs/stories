package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Backlog implements Scorable {
    private Map<String, ? extends BacklogItem> issues = new HashMap<>();
    private Map<String, List<String>> propertyChangesByIssueKey;

    public Backlog(Map<String, ? extends BacklogItem> issues) {
        this.issues = issues;
        this.propertyChangesByIssueKey = new HashMap<>();
    }
}
