
package org.craftsmenlabs.stories.isolator.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
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
//    @JsonIgnore
//    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//    @JsonAnyGetter
//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    @JsonAnySetter
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }

}
