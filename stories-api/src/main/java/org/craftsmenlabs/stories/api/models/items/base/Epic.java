package org.craftsmenlabs.stories.api.models.items.base;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("EPIC")
public class Epic extends BacklogItem {
    private String summary;
    private String description;
    private String goal;

    @Builder
    public Epic(String summary, String description, String goal, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(key, rank, externalURI, updatedAt, createdAt);
        this.summary = summary;
        this.goal = goal;
        this.description = description;
    }

    public static Epic empty(){
        return Epic.builder().summary("").description("").goal("").key("").rank("").build();
    }
}
