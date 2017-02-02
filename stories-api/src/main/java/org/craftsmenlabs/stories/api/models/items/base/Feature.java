package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feature extends BacklogItem {
    private String summary;
    private String userstory;
    private String acceptanceCriteria;
    private Float estimation;


    @Builder
    public Feature(String summary, String userstory, String acceptanceCriteria, Float estimation, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt, float potentialPoints) {
        super(key, rank, externalURI, updatedAt, createdAt, potentialPoints);
        this.summary = summary;
        this.userstory = userstory;
        this.acceptanceCriteria = acceptanceCriteria;
        this.estimation = estimation;
    }
}