package org.craftsmenlabs.stories.api.models.items.types;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.base.TeamTask;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Feature.class, name = "FEATURE"),
        @JsonSubTypes.Type(value = Bug.class, name = "BUG"),
        @JsonSubTypes.Type(value = Epic.class, name = "EPIC"),
        @JsonSubTypes.Type(value = TeamTask.class, name = "TEAM_TASK")
})
public abstract class BacklogItem implements Rankable, Scorable {
    private String key;
    private String rank;
    private String externalURI;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
