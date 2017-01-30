package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

import java.util.Map;

public class BugConverter extends AbstractJiraConverter<Bug> {
    public BugConverter(FieldMappingConfig config, SourceConfig sourceConfig) {
        super(config, sourceConfig);
    }

    @Override
    public Bug convert(JiraJsonIssue jiraJsonIssue) {
        Bug bug = new Bug();


        Map<String, Object> props = jiraJsonIssue.getFields().getAdditionalProperties();

        bug.setSummary(jiraJsonIssue.getFields().getSummary());

        bug.setDescription(jiraJsonIssue.getFields().getDescription());
        bug.setPriority(jiraJsonIssue.getFields().getPriority().getName());
        bug.setAcceptationCriteria((String) props.get(config.getBug().getAcceptationCriteria()));
        bug.setExpectedBehavior((String) props.get(config.getBug().getExpectedBehavior()));
        bug.setEnvironment((String) props.get(config.getBug().getEnvironment()));
        bug.setReproductionPath((String) props.get(config.getBug().getReproductionPath()));

        return (Bug) fillDefaultInfo(jiraJsonIssue, bug);
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{"bug"};
    }
}
