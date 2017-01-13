package org.craftsmenlabs.stories.api.models.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.ScrumItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamTask implements ScrumItem {
    private String key;
    private String rank;
    private String externalURI;

    private String summary;
    private String description;
    private String acceptationCriteria;
    private Float estimation;
}
