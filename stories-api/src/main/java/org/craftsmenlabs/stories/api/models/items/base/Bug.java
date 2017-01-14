package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bug extends BacklogItem {
    private String summary;
    private String description;
    private String reproductionPath;
    private String software;
    private String expectedBehavior;
    private String acceptationCriteria;
    private String priority;

    @Builder
    public Bug(String summary, String description, String reproductionPath, String software, String expectedBehavior, String acceptationCriteria, String priority, String key, String rank, String externalURI) {
        super(key, rank, externalURI);
        this.summary = summary;
        this.description = description;
        this.reproductionPath = reproductionPath;
        this.software = software;
        this.expectedBehavior = expectedBehavior;
        this.acceptationCriteria = acceptationCriteria;
        this.priority = priority;
    }
}
