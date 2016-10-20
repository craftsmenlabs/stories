
package org.craftsmenlabs.stories.isolator.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "self",
    "description",
    "iconUrl",
    "name",
    "id",
    "statusCategory"
})
public class Status {

    @JsonProperty("self")
    public String self;
    @JsonProperty("description")
    public String description;
    @JsonProperty("iconUrl")
    public String iconUrl;
    @JsonProperty("name")
    public String name;
    @JsonProperty("id")
    public String id;
//    @JsonProperty("statusCategory")
//    public StatusCategory statusCategory;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
