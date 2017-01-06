
package org.craftsmenlabs.stories.isolator.model.jira;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//No class-fields are declared, so that all fields are put into the additionalProperties.
//Getters and setters are declared, so that original fields can still be accessed.
public class Fields {
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Fields() {
    }

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

    public Object getTimespent() {return this.additionalProperties.get("imespent"); }

    public Object getTimeoriginalestimate() {
        return (Object) this.additionalProperties.get("timeoriginalestimate");
    }

    public String getDescription() {
        return (String) this.additionalProperties.get("description");
    }

    public Object getAggregatetimespent() {
        return (Object) this.additionalProperties.get("aggregatetimespent");
    }

    public Object getResolution() {
        return (Object) this.additionalProperties.get("resolution");
    }

    public Object getAggregatetimeestimate() {
        return (Object) this.additionalProperties.get("aggregatetimeestimate");
    }

    public Object getResolutiondate() {
        return (Object) this.additionalProperties.get("resolutiondate");
    }

    public Integer getWorkratio() {
        return (Integer) this.additionalProperties.get("workratio");
    }

    public String getSummary() {
        return (String) this.additionalProperties.get("summary");
    }

    public String getLastViewed() {
        return (String) this.additionalProperties.get("lastViewed");
    }

    public List<Object> getLabels() {
        return (List<Object>) this.additionalProperties.get("labels");
    }

    public Object getEnvironment() {
        return (Object) this.additionalProperties.get("environment");
    }

    public Object getTimeestimate() {
        return (Object) this.additionalProperties.get("timeestimate");
    }

    public Object getAggregatetimeoriginalestimate() {
        return (Object) this.additionalProperties.get("aggregatetimeoriginalestimate");
    }

    public List<Object> getVersions() {
        return (List<Object>) this.additionalProperties.get("versions");
    }

    public Object getDuedate() {
        return (Object) this.additionalProperties.get("duedate");
    }

    public Issuetype getIssuetype() {
        return (Issuetype) this.additionalProperties.get("issuetype");
    }

    public Status getStatus() {
        return (Status) this.additionalProperties.get("status");
    }

    public Priority getPriority() {
        return (Priority) this.additionalProperties.get("priority");
    }

/////////////////
//    SETTERS
    public void setTimespent(Object timespent) {
        this.getAdditionalProperties().put("timespent", timespent);
    }

    public void setTimeoriginalestimate(Object timeoriginalestimate) {
        this.getAdditionalProperties().put("timeoriginalestimate", timeoriginalestimate);
    }

    public void setDescription(String description) {
        this.getAdditionalProperties().put("description", description);
    }

    public void setAggregatetimespent(Object aggregatetimespent) {
        this.getAdditionalProperties().put("aggregatetimespent", aggregatetimespent);
    }

    public void setResolution(Object resolution) {
        this.getAdditionalProperties().put("resolution", resolution);
    }

    public void setAggregatetimeestimate(Object aggregatetimeestimate) {
        this.getAdditionalProperties().put("aggregatetimeestimate", aggregatetimeestimate);
    }

    public void setResolutiondate(Object resolutiondate) {
        this.getAdditionalProperties().put("resolutiondate", resolutiondate);
    }

    public void setWorkratio(Integer workratio) {
        this.getAdditionalProperties().put("workratio", workratio);
    }

    public void setSummary(String summary) {
        this.getAdditionalProperties().put("summary", summary);
    }

    public void setLastViewed(String lastViewed) {
        this.getAdditionalProperties().put("lastViewed", lastViewed);
    }

    public void setLabels(List<Object> labels) {
        this.getAdditionalProperties().put("labels", labels);
    }

    public void setEnvironment(Object environment) {
        this.getAdditionalProperties().put("environment", environment);
    }

    public void setTimeestimate(Object timeestimate) {
        this.getAdditionalProperties().put("timeestimate", timeestimate);
    }

    public void setAggregatetimeoriginalestimate(Object aggregatetimeoriginalestimate) {
        this.getAdditionalProperties().put("aggregatetimeoriginalestimate", aggregatetimeoriginalestimate);
    }

    public void setVersions(List<Object> versions) {
        this.getAdditionalProperties().put("versions", versions);
    }

    public void setDuedate(Object duedate) {
        this.getAdditionalProperties().put("duedate", duedate);
    }

    public void setIssuetype(Issuetype issuetype) {
        this.getAdditionalProperties().put("issuetype", issuetype);
    }

    public void setStatus(Status status) {
        this.getAdditionalProperties().put("status", status);
    }

    public void setPriority(Priority priority) {
        this.getAdditionalProperties().put("priority", priority);
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Fields)) return false;
        final Fields other = (Fields) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$timespent = this.getTimespent();
        final Object other$timespent = other.getTimespent();
        if (this$timespent == null ? other$timespent != null : !this$timespent.equals(other$timespent)) return false;
        final Object this$timeoriginalestimate = this.getTimeoriginalestimate();
        final Object other$timeoriginalestimate = other.getTimeoriginalestimate();
        if (this$timeoriginalestimate == null ? other$timeoriginalestimate != null : !this$timeoriginalestimate.equals(other$timeoriginalestimate))
            return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$aggregatetimespent = this.getAggregatetimespent();
        final Object other$aggregatetimespent = other.getAggregatetimespent();
        if (this$aggregatetimespent == null ? other$aggregatetimespent != null : !this$aggregatetimespent.equals(other$aggregatetimespent))
            return false;
        final Object this$resolution = this.getResolution();
        final Object other$resolution = other.getResolution();
        if (this$resolution == null ? other$resolution != null : !this$resolution.equals(other$resolution))
            return false;
        final Object this$aggregatetimeestimate = this.getAggregatetimeestimate();
        final Object other$aggregatetimeestimate = other.getAggregatetimeestimate();
        if (this$aggregatetimeestimate == null ? other$aggregatetimeestimate != null : !this$aggregatetimeestimate.equals(other$aggregatetimeestimate))
            return false;
        final Object this$resolutiondate = this.getResolutiondate();
        final Object other$resolutiondate = other.getResolutiondate();
        if (this$resolutiondate == null ? other$resolutiondate != null : !this$resolutiondate.equals(other$resolutiondate))
            return false;
        final Object this$workratio = this.getWorkratio();
        final Object other$workratio = other.getWorkratio();
        if (this$workratio == null ? other$workratio != null : !this$workratio.equals(other$workratio)) return false;
        final Object this$summary = this.getSummary();
        final Object other$summary = other.getSummary();
        if (this$summary == null ? other$summary != null : !this$summary.equals(other$summary)) return false;
        final Object this$lastViewed = this.getLastViewed();
        final Object other$lastViewed = other.getLastViewed();
        if (this$lastViewed == null ? other$lastViewed != null : !this$lastViewed.equals(other$lastViewed))
            return false;
        final Object this$labels = this.getLabels();
        final Object other$labels = other.getLabels();
        if (this$labels == null ? other$labels != null : !this$labels.equals(other$labels)) return false;
        final Object this$environment = this.getEnvironment();
        final Object other$environment = other.getEnvironment();
        if (this$environment == null ? other$environment != null : !this$environment.equals(other$environment))
            return false;
        final Object this$timeestimate = this.getTimeestimate();
        final Object other$timeestimate = other.getTimeestimate();
        if (this$timeestimate == null ? other$timeestimate != null : !this$timeestimate.equals(other$timeestimate))
            return false;
        final Object this$aggregatetimeoriginalestimate = this.getAggregatetimeoriginalestimate();
        final Object other$aggregatetimeoriginalestimate = other.getAggregatetimeoriginalestimate();
        if (this$aggregatetimeoriginalestimate == null ? other$aggregatetimeoriginalestimate != null : !this$aggregatetimeoriginalestimate.equals(other$aggregatetimeoriginalestimate))
            return false;
        final Object this$versions = this.getVersions();
        final Object other$versions = other.getVersions();
        if (this$versions == null ? other$versions != null : !this$versions.equals(other$versions)) return false;
        final Object this$duedate = this.getDuedate();
        final Object other$duedate = other.getDuedate();
        if (this$duedate == null ? other$duedate != null : !this$duedate.equals(other$duedate)) return false;
        final Object this$issuetype = this.getIssuetype();
        final Object other$issuetype = other.getIssuetype();
        if (this$issuetype == null ? other$issuetype != null : !this$issuetype.equals(other$issuetype)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$priority = this.getPriority();
        final Object other$priority = other.getPriority();
        if (this$priority == null ? other$priority != null : !this$priority.equals(other$priority)) return false;
        final Object this$additionalProperties = this.getAdditionalProperties();
        final Object other$additionalProperties = other.getAdditionalProperties();
        if (this$additionalProperties == null ? other$additionalProperties != null : !this$additionalProperties.equals(other$additionalProperties))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $timespent = this.getTimespent();
        result = result * PRIME + ($timespent == null ? 43 : $timespent.hashCode());
        final Object $timeoriginalestimate = this.getTimeoriginalestimate();
        result = result * PRIME + ($timeoriginalestimate == null ? 43 : $timeoriginalestimate.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $aggregatetimespent = this.getAggregatetimespent();
        result = result * PRIME + ($aggregatetimespent == null ? 43 : $aggregatetimespent.hashCode());
        final Object $resolution = this.getResolution();
        result = result * PRIME + ($resolution == null ? 43 : $resolution.hashCode());
        final Object $aggregatetimeestimate = this.getAggregatetimeestimate();
        result = result * PRIME + ($aggregatetimeestimate == null ? 43 : $aggregatetimeestimate.hashCode());
        final Object $resolutiondate = this.getResolutiondate();
        result = result * PRIME + ($resolutiondate == null ? 43 : $resolutiondate.hashCode());
        final Object $workratio = this.getWorkratio();
        result = result * PRIME + ($workratio == null ? 43 : $workratio.hashCode());
        final Object $summary = this.getSummary();
        result = result * PRIME + ($summary == null ? 43 : $summary.hashCode());
        final Object $lastViewed = this.getLastViewed();
        result = result * PRIME + ($lastViewed == null ? 43 : $lastViewed.hashCode());
        final Object $labels = this.getLabels();
        result = result * PRIME + ($labels == null ? 43 : $labels.hashCode());
        final Object $environment = this.getEnvironment();
        result = result * PRIME + ($environment == null ? 43 : $environment.hashCode());
        final Object $timeestimate = this.getTimeestimate();
        result = result * PRIME + ($timeestimate == null ? 43 : $timeestimate.hashCode());
        final Object $aggregatetimeoriginalestimate = this.getAggregatetimeoriginalestimate();
        result = result * PRIME + ($aggregatetimeoriginalestimate == null ? 43 : $aggregatetimeoriginalestimate.hashCode());
        final Object $versions = this.getVersions();
        result = result * PRIME + ($versions == null ? 43 : $versions.hashCode());
        final Object $duedate = this.getDuedate();
        result = result * PRIME + ($duedate == null ? 43 : $duedate.hashCode());
        final Object $issuetype = this.getIssuetype();
        result = result * PRIME + ($issuetype == null ? 43 : $issuetype.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $priority = this.getPriority();
        result = result * PRIME + ($priority == null ? 43 : $priority.hashCode());
        final Object $additionalProperties = this.getAdditionalProperties();
        result = result * PRIME + ($additionalProperties == null ? 43 : $additionalProperties.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Fields;
    }

    public String toString() {
        return "org.craftsmenlabs.stories.isolator.model.jira.Fields(timespent=" + this.getTimespent() + ", timeoriginalestimate=" + this.getTimeoriginalestimate() + ", description=" + this.getDescription() + ", aggregatetimespent=" + this.getAggregatetimespent() + ", resolution=" + this.getResolution() + ", aggregatetimeestimate=" + this.getAggregatetimeestimate() + ", resolutiondate=" + this.getResolutiondate() + ", workratio=" + this.getWorkratio() + ", summary=" + this.getSummary() + ", lastViewed=" + this.getLastViewed() + ", labels=" + this.getLabels() + ", environment=" + this.getEnvironment() + ", timeestimate=" + this.getTimeestimate() + ", aggregatetimeoriginalestimate=" + this.getAggregatetimeoriginalestimate() + ", versions=" + this.getVersions() + ", duedate=" + this.getDuedate() + ", issuetype=" + this.getIssuetype() + ", status=" + this.getStatus() + ", priority=" + this.getPriority() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
}
