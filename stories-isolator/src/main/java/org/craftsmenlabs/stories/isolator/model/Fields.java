
package org.craftsmenlabs.stories.isolator.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    "customfield_10310",
    "customfield_10302",
    "customfield_10401",
    "customfield_10306",
    "aggregatetimeestimate",
    "customfield_10307",
    "customfield_10308",
    "resolutiondate",
    "customfield_10309",
    "workratio",
    "summary",
    "lastViewed",
    "watches",
    "creator",
    "subtasks",
    "created",
    "reporter",
    "customfield_10000",
    "aggregateprogress",
    "priority",
    "customfield_10100",
    "customfield_10200",
    "customfield_10201",
    "customfield_10300",
    "labels",
    "customfield_10301",
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
    @JsonProperty("customfield_10310")
    public Object customfield10310;
    @JsonProperty("customfield_10302")
    public String customfield10302;


    @JsonProperty("customfield_10401")
    public String rank;


    @JsonProperty("customfield_10306")
    public Object customfield10306;
    @JsonProperty("aggregatetimeestimate")
    public Object aggregatetimeestimate;
    @JsonProperty("customfield_10307")
    public Object customfield10307;

    @JsonProperty("customfield_10308")
    public Float estimation;

    @JsonProperty("resolutiondate")
    public Object resolutiondate;
    @JsonProperty("customfield_10309")
    public Object customfield10309;
    @JsonProperty("workratio")
    public Integer workratio;
    @JsonProperty("summary")
    public String summary;
    @JsonProperty("lastViewed")
    public String lastViewed;

    @JsonProperty("customfield_10100")
    public Object customfield10100;
    @JsonProperty("customfield_10200")
    public Object customfield10200;
    @JsonProperty("customfield_10201")
    public Object customfield10201;
    @JsonProperty("customfield_10300")
    public Object customfield10300;
    @JsonProperty("labels")
    public List<Object> labels = new ArrayList<Object>();
    @JsonProperty("customfield_10301")
    public Object customfield10301;
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
