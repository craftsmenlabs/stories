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
@JsonTypeName("TEAM_TASK")
public class TeamTask extends BacklogItem {
    private String summary;
    private String description;
    private String acceptationCriteria;
    private Double estimation;

    public static TeamTask empty(){
        return TeamTask.builder().summary("").description("").acceptationCriteria("").estimation(0.0).build();
    }

    @Builder
    public TeamTask(String summary, String description, String acceptationCriteria, Double estimation, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(key, rank, externalURI, updatedAt, createdAt);
        this.summary = summary;
        this.description = description;
        this.acceptationCriteria = acceptationCriteria;
        this.estimation = estimation;
    }
}
