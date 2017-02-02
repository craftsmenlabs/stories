package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Responsible for
 *
 * @param <T>
 */
public abstract class AbstractJiraConverter<T extends BacklogItem> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d'T'H:m:s['.000']Z");
    protected FieldMappingConfig config;
    protected SourceConfig sourceConfig;

    public AbstractJiraConverter(FieldMappingConfig config, SourceConfig sourceConfig) {
        this.config = config;
        this.sourceConfig = sourceConfig;
    }

    public abstract T convert(JiraJsonIssue jiraJsonIssue);

    public boolean supportsIssue(JiraJsonIssue issue) {
        return Arrays.stream(this.getSupportedTypes())
                .anyMatch(supportedType ->
                        StringUtils.equalsIgnoreCase(supportedType, issue.getType()));
    }

    public abstract String[] getSupportedTypes();

    protected BacklogItem fillDefaultInfo(JiraJsonIssue jiraJsonIssue, BacklogItem item) {
        String rank = (String) jiraJsonIssue.getFields().getAdditionalProperties().get(config.getRank());
        if (StringUtils.isEmpty(rank)) {
            throw new StoriesException(
                    "The rank field mapping was not defined in your application yaml or parameters. " +
                            "Is the field mapping configured correctly?");
        }
        item.setRank(rank);

        String createdAt = (String) jiraJsonIssue.getFields().getAdditionalProperties().get("created");
        String updatedAt = (String) jiraJsonIssue.getFields().getAdditionalProperties().get("updated");
        if (StringUtils.isNotEmpty(createdAt)) {
            item.setCreatedAt(LocalDateTime.parse(createdAt, formatter));
        }
        if (StringUtils.isNotEmpty(updatedAt)) {
            item.setUpdatedAt(LocalDateTime.parse(updatedAt, formatter));
        }
        item.setKey(jiraJsonIssue.getKey());
        item.setExternalURI(
                sourceConfig.getJira().getUrl() +
                        "/projects/" + sourceConfig.getJira().getProjectKey() +
                        "/issues/" + jiraJsonIssue.getKey()
        );

        return item;
    }
}
