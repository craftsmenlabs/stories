package org.craftsmenlabs.stories.api.models.scrumitems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Backlog implements ScrumItem{
    @JsonProperty("issues")
    private List<Issue> issues;
}
