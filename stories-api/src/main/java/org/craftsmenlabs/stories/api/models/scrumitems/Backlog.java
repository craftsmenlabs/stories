package org.craftsmenlabs.stories.api.models.scrumitems;

import java.util.List;
import lombok.Data;

@Data
public class Backlog implements ScrumItem{
    private List<Issue> issues;
}
