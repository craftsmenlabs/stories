package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamTask extends BacklogItem {
    private String summary;
    private String description;
    private Criteria acceptationCriteria;
    private Estimation estimation;

    public static TeamTask empty(){
        return TeamTask.builder().summary("").description("").acceptationCriteria(new Criteria()).estimation(new Estimation()).build();
    }

    @Builder
    public TeamTask(String summary, String description, Criteria acceptationCriteria, Estimation estimation, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(key, rank, externalURI, updatedAt, createdAt);
        this.summary = summary;
        this.description = description;
        this.acceptationCriteria = acceptationCriteria;
        this.estimation = estimation;
    }
}
