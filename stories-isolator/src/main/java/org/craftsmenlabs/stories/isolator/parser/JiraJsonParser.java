package org.craftsmenlabs.stories.isolator.parser;

import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.BugConverter;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.EpicConverter;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.FeatureConverter;
import org.craftsmenlabs.stories.isolator.parser.converters.jira.TeamTaskConverter;

import java.util.List;
import java.util.stream.Collectors;

public class JiraJsonParser {
    private FilterConfig filterConfig;

    private FeatureConverter featureConverter;
    private BugConverter bugConverter;
    private EpicConverter epicConverter;
    private TeamTaskConverter teamTaskConverter;

    public JiraJsonParser(StorynatorLogger logger, FieldMappingConfig fieldMapping, FilterConfig filterConfig, SourceConfig sourceConfig) {


        this.filterConfig = filterConfig;

        this.featureConverter = new FeatureConverter(logger, fieldMapping, sourceConfig);
        this.bugConverter = new BugConverter(fieldMapping, sourceConfig);
        this.epicConverter = new EpicConverter(fieldMapping, sourceConfig);
        this.teamTaskConverter = new TeamTaskConverter(logger, fieldMapping, sourceConfig);
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

        backlog.setTeamTasks(jiraJsonIssues.stream()
                .filter(teamTaskConverter::supportsIssue)
                .map(teamTaskConverter::convert)
                .collect(Collectors.toList()));

        return backlog;
    }
}
