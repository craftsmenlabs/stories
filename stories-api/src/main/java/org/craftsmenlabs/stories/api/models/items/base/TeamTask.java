package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamTask extends BacklogItem {
    private String summary;
    private String description;
    private String acceptationCriteria;
    private Float estimation;

    public TeamTask(String summary, String description, String acceptationCriteria, Float estimation, String key, String rank, String externalURI) {
        super(key, rank, externalURI);
        this.summary = summary;
        this.description = description;
        this.acceptationCriteria = acceptationCriteria;
        this.estimation = estimation;
    }
}
