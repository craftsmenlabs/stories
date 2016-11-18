package org.craftsmenlabs.stories.isolator.parser;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class JiraJsonParser {

    private final Logger logger = LoggerFactory.getLogger(JiraJsonParser.class);
    private final SentenceSplitter sentenceSplitter = new SentenceSplitter();
    private FieldMappingConfig fieldMapping;
    private FilterConfig filterConfig;

    public JiraJsonParser(FieldMappingConfig fieldMapping, FilterConfig filterConfig) {
        this.fieldMapping = fieldMapping;
        this.filterConfig = filterConfig;
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
                .filter(jiraJsonIssue -> jiraJsonIssue.getType().equals("story"))
                .map(this::convertJsonIssueToFeature)
                .filter(issue -> StringUtils.isNotEmpty(issue.getUserstory()))
                .collect(Collectors.toList())
        );

        return backlog;
    }

    public boolean hasNoValidDescription(JiraJsonIssue jiraJsonIssue) {
        return jiraJsonIssue.getFields().getDescription() == null || jiraJsonIssue.getFields().getDescription().isEmpty();
    }

    private Feature convertJsonIssueToFeature(JiraJsonIssue jiraJsonIssue) {
        Feature feature;

        if (hasNoValidDescription(jiraJsonIssue)) {
            feature = new Feature();
        } else {
            feature = sentenceSplitter.splitSentence(jiraJsonIssue.getFields().getDescription());
        }

        feature.setSummary(jiraJsonIssue.getFields().getSummary());
        feature.setKey(jiraJsonIssue.getKey());
        feature.setIssueType(jiraJsonIssue.getFields().getIssuetype().getName());

        String criteriaKey = fieldMapping.getFeature().getAcceptanceCriteria();
        if (StringUtils.isNotEmpty(criteriaKey) && jiraJsonIssue.getFields().getAdditionalProperties().containsKey(criteriaKey)) {
            // We should get the acceptence Criteria from this field
            feature.setAcceptanceCriteria((String) jiraJsonIssue.getFields().getAdditionalProperties().get(criteriaKey));
        }

        Map<String, Object> additionalProps = jiraJsonIssue.getFields().getAdditionalProperties();
        Map<String, String> stringProps = new HashMap<>();
        for (Entry<String, Object> entries : additionalProps.entrySet()) {
            if (entries.getValue() != null) {
                stringProps.put(entries.getKey(), entries.getValue().toString());
            } else {
                stringProps.put(entries.getKey(), "");
            }
        }

        String rank = stringProps.get(fieldMapping.getFeature().getRank());
        if (rank == null || StringUtils.isEmpty(rank)) {
            throw new StoriesException(
                    "The rank field mapping was not defined in your application yaml or parameters. " +
                            "Is the field mapping configured correctly?");
        }
        feature.setRank(rank);

        float estimation = 0f;
        try {
            if (stringProps.get(fieldMapping.getFeature().getEstimation()) != null) {
                estimation = Float.parseFloat(stringProps.get(fieldMapping.getFeature().getEstimation()));
            }
        } catch (NumberFormatException nfe) {
            logger.warn("Parsing of estimation to float failed. By default set to 0.0");
        }
        feature.setEstimation(estimation);

        return feature;
    }
}
