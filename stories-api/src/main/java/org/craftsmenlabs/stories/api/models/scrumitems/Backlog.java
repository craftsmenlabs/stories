package org.craftsmenlabs.stories.api.models.scrumitems;

import lombok.Data;

import java.util.List;

@Data
public class Backlog implements ScrumItem{
    List<Issue> issues;
}
