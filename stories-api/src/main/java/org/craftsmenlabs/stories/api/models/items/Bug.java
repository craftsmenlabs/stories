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
public class Bug implements ScrumItem {
    private String key;
    private String rank;
    private String externalURI;

    private String summary;
    private String description;
    private String reproductionPath;
    private String software;
    private String expectedBehavior;
    private String acceptationCriteria;
    private String priority;
}
