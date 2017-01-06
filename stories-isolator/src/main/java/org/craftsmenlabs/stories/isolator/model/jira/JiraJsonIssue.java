
package org.craftsmenlabs.stories.isolator.model.jira;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "expand",
    "id",
    "self",
    "key",
    "fields"
})
public class JiraJsonIssue {

    @JsonProperty("expand")
    public String expand;
    @JsonProperty("id")
    public String id;
    @JsonProperty("self")
    public String self;
    @JsonProperty("key")
    public String key;
    @JsonProperty("fields")
    public Fields fields;

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

    // Shortcut to get the status field
    @JsonIgnore
    public String getStatus() {
        return this.fields.getStatus().getStatusCategory().getName();
    }

    @JsonIgnore
    public String getType() {
        return this.fields.getIssuetype().getName().toLowerCase();
    }
}
