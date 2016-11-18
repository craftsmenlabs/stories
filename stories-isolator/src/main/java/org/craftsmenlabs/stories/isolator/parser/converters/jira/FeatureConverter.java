package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FeatureConverter {
    private final Logger logger = LoggerFactory.getLogger(FeatureConverter.class);

    private final SentenceSplitter sentenceSplitter = new SentenceSplitter();
    private FieldMappingConfig config;

    public FeatureConverter(FieldMappingConfig config) {
        this.config = config;
    }

    public Feature convert(JiraJsonIssue jiraJsonIssue) {
        Feature feature = new Feature();

        // Extract description
        if (hasValidDescription(jiraJsonIssue)) {
            feature = sentenceSplitter.splitSentence(feature, jiraJsonIssue.getFields().getDescription());
        }

        feature.setSummary(jiraJsonIssue.getFields().getSummary());
        feature.setKey(jiraJsonIssue.getKey());
        getAcceptanceCriteria(feature, jiraJsonIssue);

        Map<String, Object> additionalProps = jiraJsonIssue.getFields().getAdditionalProperties();

        String rank = (String) additionalProps.get(config.getFeature().getRank());
        if (StringUtils.isEmpty(rank)) {
            throw new StoriesException(
                    "The rank field mapping was not defined in your application yaml or parameters. " +
                            "Is the field mapping configured correctly?");
        }
        feature.setRank(rank);

        feature.setEstimation(this.parseEstimation((String) additionalProps.get(config.getFeature().getEstimation())));

        return feature;
    }

    private boolean hasValidDescription(JiraJsonIssue jiraJsonIssue) {
        return StringUtils.isNotEmpty(jiraJsonIssue.getFields().getDescription());
    }

    private void getAcceptanceCriteria(Feature feature, JiraJsonIssue jiraJsonIssue) {
        String criteriaKey = config.getFeature().getAcceptanceCriteria();
        if (StringUtils.isNotEmpty(criteriaKey)
                && StringUtils.isEmpty(feature.getAcceptanceCriteria())
                && jiraJsonIssue.getFields().getAdditionalProperties().containsKey(criteriaKey)) {
            // We should get the acceptance Criteria from this field
            feature.setAcceptanceCriteria((String) jiraJsonIssue.getFields().getAdditionalProperties().get(criteriaKey));
        }
    }

    private float parseEstimation(String estimationString) {
        try {
            if (StringUtils.isNotBlank(estimationString)) {
                return Float.parseFloat(estimationString);
            }
        } catch (NumberFormatException nfe) {
            logger.warn("Parsing of estimation to float failed. By default set to 0.0");
        }
        return 0f;
    }
}
