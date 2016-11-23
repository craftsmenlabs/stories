package org.craftsmenlabs.stories.api.models.scrumitems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bug implements ScrumItem {
    @JsonProperty("key")
    private String key;
    private String rank;

    private String summary;
    private String reproductionPath;
    private String software;
    private String expectedBehavior;
    private String acceptationCriteria;
    private String priority;
}
