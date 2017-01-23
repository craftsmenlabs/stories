package org.craftsmenlabs.stories.isolator.parser;

import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.*;

import java.util.*;
import java.util.stream.Collectors;

public class JiraJsonParser {
    private List<AbstractJiraConverter> converters;
    private FilterConfig filterConfig;

    public JiraJsonParser(StorynatorLogger logger, FieldMappingConfig fieldMapping, FilterConfig filterConfig, SourceConfig sourceConfig) {
        this.filterConfig = filterConfig;

        this.converters = Arrays.asList(
                new FeatureConverter(logger, fieldMapping, sourceConfig),
                new BugConverter(fieldMapping, sourceConfig),
                new EpicConverter(fieldMapping, sourceConfig),
                new TeamTaskConverter(logger, fieldMapping, sourceConfig)
        );

    }

    public Backlog parse(JiraBacklog jiraBacklog) {
        if (jiraBacklog == null) {
            return null;
        }

        // Filter issues on status and convert them
        Map<String, ? extends BacklogItem> backlogItems = jiraBacklog.getJiraJsonIssues().stream()
                .filter(jiraJsonIssue -> jiraJsonIssue.getStatus().equals(filterConfig.getStatus()))
                .map(jiraJsonIssue -> {
                    Optional<AbstractJiraConverter> converter = converters.stream().filter(conv ->
                            conv.supportsIssue(jiraJsonIssue))
                            .findFirst();
                    //noinspection OptionalIsPresent
                    return converter.isPresent() ? converter.get().convert(jiraJsonIssue) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(BacklogItem::getKey, c -> c));

        return new Backlog(backlogItems);
    }
}
