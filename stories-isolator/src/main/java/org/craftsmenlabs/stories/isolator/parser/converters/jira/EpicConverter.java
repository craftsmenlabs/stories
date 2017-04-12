package org.craftsmenlabs.stories.isolator.parser.converters.jira;


import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

public class EpicConverter extends AbstractJiraConverter<Epic> {
    public EpicConverter(FieldMappingConfig config, SourceConfig sourceConfig) {
        super(config, sourceConfig);
    }

    @Override
    public Epic convert(JiraJsonIssue jiraJsonIssue) {
        Epic epic = new Epic();
        epic.setSummary(jiraJsonIssue.getFields().getSummary());
        epic.setDescription(jiraJsonIssue.getFields().getDescription());
        epic.setGoal((String) jiraJsonIssue.getFields().getAdditionalProperties().get(config.getEpic().getGoal()));
        return (Epic) fillDefaultInfo(jiraJsonIssue, epic);
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{"epic"};
    }
}
