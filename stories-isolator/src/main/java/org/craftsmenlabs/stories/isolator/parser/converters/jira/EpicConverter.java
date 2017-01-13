package org.craftsmenlabs.stories.isolator.parser.converters.jira;


import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.Epic;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

public class EpicConverter extends AbstractJiraConverter<Epic> {
    public EpicConverter(FieldMappingConfig config, SourceConfig sourceConfig) {
        super(config, sourceConfig);
    }

    @Override
    public Epic convert(JiraJsonIssue jiraJsonIssue) {
        Epic epic = new Epic();

        epic.setKey(jiraJsonIssue.getKey());
        epic.setSummary(jiraJsonIssue.getFields().getSummary());

        epic.setExternalURI(
                sourceConfig.getJira().getUrl() +
                        "/projects/" + sourceConfig.getJira().getProjectKey() +
                        "/issues/" + jiraJsonIssue.getKey()
        );

        epic.setRank((String) jiraJsonIssue.getFields().getAdditionalProperties().get(config.getRank()));
        epic.setGoal((String) jiraJsonIssue.getFields().getAdditionalProperties().get(config.getEpic().getGoal()));
        return epic;
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{"epic"};
    }
}
