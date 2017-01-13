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
public class Feature implements ScrumItem {
    private String key;
    private String rank;
    private String externalURI;

    private String summary;
    private String userstory;
    private String acceptanceCriteria;
    private Float estimation;
}