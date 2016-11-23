package org.craftsmenlabs.stories.api.models.scrumitems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Epic implements ScrumItem {
    private String key;
    private String rank;
    private String summary;
    private String goal;
}
