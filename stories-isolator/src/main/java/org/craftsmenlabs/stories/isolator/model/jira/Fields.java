
package org.craftsmenlabs.stories.isolator.model.jira;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "issuetype",
    "components",
    "timespent",
    "timeoriginalestimate",
    "description",
    "project",
    "fixVersions",
    "aggregatetimespent",
    "resolution",
    "aggregatetimeestimate",
    "resolutiondate",
    "workratio",
    "summary",
    "lastViewed",
    "watches",
    "creator",
    "subtasks",
    "created",
    "reporter",
    "aggregateprogress",
    "priority",
    "labels",
    "environment",
    "timeestimate",
    "aggregatetimeoriginalestimate",
    "versions",
    "duedate",
    "progress",
    "issuelinks",
    "votes",
    "assignee",
    "updated",
    "status"
})
public class Fields {

    @JsonProperty("timespent")
    public Object timespent;
    @JsonProperty("timeoriginalestimate")
    public Object timeoriginalestimate;
    @JsonProperty("description")
    public String description;
    @JsonProperty("aggregatetimespent")
    public Object aggregatetimespent;
    @JsonProperty("resolution")
    public Object resolution;
    @JsonProperty("aggregatetimeestimate")
    public Object aggregatetimeestimate;
    @JsonProperty("resolutiondate")
    public Object resolutiondate;
    @JsonProperty("workratio")
    public Integer workratio;
    @JsonProperty("summary")
    public String summary;
    @JsonProperty("lastViewed")
    public String lastViewed;
    @JsonProperty("labels")
    public List<Object> labels = new ArrayList<Object>();
    @JsonProperty("environment")
    public Object environment;
    @JsonProperty("timeestimate")
    public Object timeestimate;
    @JsonProperty("aggregatetimeoriginalestimate")
    public Object aggregatetimeoriginalestimate;
    @JsonProperty("versions")
    public List<Object> versions = new ArrayList<Object>();
    @JsonProperty("duedate")
    public Object duedate;
    @JsonProperty("issuetype")
    public Issuetype issuetype;
    @JsonProperty("status")
    public Status status;
    @JsonProperty("priority")
    public Priority priority;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonIgnore
    public Map<String, String> getAdditionalPropertiesAsString() {
        return this.additionalProperties.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
                entry.getValue() != null ? entry.getValue().toString() : ""
        ));
    }



    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
