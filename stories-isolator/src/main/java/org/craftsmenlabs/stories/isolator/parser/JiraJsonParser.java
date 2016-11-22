package org.craftsmenlabs.stories.isolator.parser;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.BugConverter;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.EpicConverter;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.FeatureConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class JiraJsonParser {
    private final Logger logger = LoggerFactory.getLogger(JiraJsonParser.class);
    private FilterConfig filterConfig;

    private FeatureConverter featureConverter;
    private BugConverter bugConverter;
    private EpicConverter epicConverter;

    public JiraJsonParser(FieldMappingConfig fieldMapping, FilterConfig filterConfig) {
        this.filterConfig = filterConfig;

        this.featureConverter = new FeatureConverter(fieldMapping);
        this.bugConverter = new BugConverter(fieldMapping);
        this.epicConverter = new EpicConverter(fieldMapping);
    }

    public Backlog parse(JiraBacklog jiraBacklog) {
        if (jiraBacklog == null) {
            return null;
        }

        Backlog backlog = new Backlog();

        // Filter issues on status
        List<JiraJsonIssue> jiraJsonIssues = jiraBacklog.getJiraJsonIssues().stream()
                .filter(jiraJsonIssue -> jiraJsonIssue.getStatus().equals(filterConfig.getStatus()))
                .collect(Collectors.toList());

        // Filter and convert features
        backlog.setFeatures(jiraJsonIssues.stream()
                .filter(featureConverter::supportsIssue)
                .map(featureConverter::convert)
                .filter(issue -> StringUtils.isNotEmpty(issue.getUserstory()))
                .collect(Collectors.toList())
        );

        // Filter and convert bugs
        backlog.setBugs(jiraJsonIssues.stream()
                .filter(bugConverter::supportsIssue)
                .map(bugConverter::convert)
                .collect(Collectors.toList()));

        backlog.setEpics(jiraJsonIssues.stream()
                .filter(epicConverter::supportsIssue)
                .map(epicConverter::convert)
                .collect(Collectors.toList()));

        return backlog;
    }
}
