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
@JsonTypeName("BUG")
public class Bug extends BacklogItem {
    private String summary;
    private String description;
    private String reproductionPath;
    private String environment;
    private String expectedBehavior;
    private String acceptationCriteria;
    private String priority;

    @Builder
    public Bug(String summary, String description, String reproductionPath, String environment, String expectedBehavior, String acceptationCriteria, String priority, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(key, rank, externalURI, updatedAt, createdAt);
        this.summary = summary;
        this.description = description;
        this.reproductionPath = reproductionPath;
        this.environment = environment;
        this.expectedBehavior = expectedBehavior;
        this.acceptationCriteria = acceptationCriteria;
        this.priority = priority;
    }

    public static Bug empty(){
        return new Bug("", "", "","","","","");
    }
}
