package org.craftsmenlabs.stories.isolator.parser.converters.jira;


import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Epic;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

public class EpicConverter extends AbstractJiraConverter<Epic> {
    public EpicConverter(FieldMappingConfig config) {
        super(config);
    }

    @Override
    public Epic convert(JiraJsonIssue jiraJsonIssue) {
        Epic epic = new Epic();

        epic.setKey(jiraJsonIssue.getKey());
        epic.setSummary(jiraJsonIssue.getFields().getSummary());
        epic.setRank((String) jiraJsonIssue.getFields().getAdditionalProperties().get(config.getRank()));
        epic.setGoal((String) jiraJsonIssue.getFields().getAdditionalProperties().get(config.getEpic().getGoal()));
        return epic;
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{"epic"};
    }
}
