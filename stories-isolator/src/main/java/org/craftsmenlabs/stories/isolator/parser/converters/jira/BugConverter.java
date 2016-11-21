package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Bug;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

import java.util.Map;

public class BugConverter {
    private FieldMappingConfig config;

    public BugConverter(FieldMappingConfig config) {
        this.config = config;
    }

    public Bug convert(JiraJsonIssue jiraJsonIssue) {
        Bug bug = new Bug();

        bug.setKey(jiraJsonIssue.getKey());

        Map<String, Object> props = jiraJsonIssue.getFields().getAdditionalProperties();

        bug.setPriority(jiraJsonIssue.getFields().getPriority().getName());
        bug.setAcceptationCriteria((String) props.get(config.getBug().getAcceptationCriteria()));
        bug.setExpectedBehavior((String) props.get(config.getBug().getExpectedBehavior()));
        bug.setSoftware((String) props.get(config.getBug().getSoftware()));
        bug.setReproductionPath((String) props.get(config.getBug().getReproductionPath()));

        return bug;
    }
}
