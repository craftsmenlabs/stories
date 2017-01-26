package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Epic extends BacklogItem {
    private String description;
    private String summary;
    private String goal;

    @Builder
    public Epic(String summary, String goal, String key, String rank, String externalURI) {
        super(key, rank, externalURI);
        this.summary = summary;
        this.goal = goal;
    }
}
