
package org.craftsmenlabs.stories.isolator.model;

import java.util.*;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "expand",
    "startAt",
    "maxResults",
    "total",
    "jiraJsonIssues"
})
public class JiraBacklog {

    @JsonProperty("expand")
    public String expand;
    @JsonProperty("startAt")
    public Integer startAt;
    @JsonProperty("maxResults")
    public Integer maxResults;
    @JsonProperty("total")
    public Integer total;
    @JsonProperty("issues")
    public List<JiraJsonIssue> jiraJsonIssues = new ArrayList<JiraJsonIssue>();
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
