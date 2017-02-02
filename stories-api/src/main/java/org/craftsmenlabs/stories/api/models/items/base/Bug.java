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
public class Bug extends BacklogItem {
    private String summary;
    private String description;
    private String reproductionPath;
    private String environment;
    private String expectedBehavior;
    private String acceptationCriteria;
    private String priority;

    @Builder
    public Bug(String summary, String description, String reproductionPath, String environment, String expectedBehavior, String acceptationCriteria, String priority, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt, float potentialPoints) {
        super(key, rank, externalURI, updatedAt, createdAt, potentialPoints);
        this.summary = summary;
        this.description = description;
        this.reproductionPath = reproductionPath;
        this.environment = environment;
        this.expectedBehavior = expectedBehavior;
        this.acceptationCriteria = acceptationCriteria;
        this.priority = priority;
    }
}
