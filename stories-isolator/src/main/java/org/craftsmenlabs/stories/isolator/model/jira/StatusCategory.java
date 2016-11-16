package org.craftsmenlabs.stories.isolator.model.jira;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusCategory {
    @JsonProperty("self")
    public String self;
    @JsonProperty("id")
    public String id;
    @JsonProperty("key")
    public String key;
    @JsonProperty("colorName")
    public String colorName;
    @JsonProperty("name")
    public String name;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
