
package org.craftsmenlabs.stories.isolator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.annotation.Generated;

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
