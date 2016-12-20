package org.craftsmenlabs.stories.api.models.scrumitems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Backlog implements ScrumItem {
    @JsonProperty("issues")
    private List<Feature> features;

    @JsonProperty("bugs")
    private List<Bug> bugs;

    @JsonProperty("epics")
    private List<Epic> epics;

    @JsonProperty("teamtasks")
    private List<TeamTask> teamTasks;

    @JsonIgnore
    public List<? super ScrumItem> getAllItems(){
        return Arrays.asList(features, bugs, epics, teamTasks);
    }
}
