package org.craftsmenlabs.stories.api.models.scrumitems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Backlog implements ScrumItem {
    @JsonProperty("issues")
    private List<Feature> features = new ArrayList<>();

    @JsonProperty("bugs")
    private List<Bug> bugs = new ArrayList<>();

    @JsonProperty("epics")
    private List<Epic> epics = new ArrayList<>();

    @JsonProperty("teamtasks")
    private List<TeamTask> teamTasks = new ArrayList<>();

    @JsonIgnore
    public List<? super ScrumItem> getAllItems(){
        return Arrays.asList(features, bugs, epics, teamTasks);
    }
}
