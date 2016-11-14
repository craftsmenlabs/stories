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
public class Issue implements ScrumItem{
    @JsonProperty("key")
    private String key;

    @JsonProperty("rank")
    private String rank;

    private String summary;
    private String userstory;
    private String acceptanceCriteria;
    private Float estimation;

    private String issueType;
}