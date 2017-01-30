package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

import java.util.Map;

public class FeatureConverter extends AbstractJiraConverter<Feature> {
    private final StorynatorLogger logger;

    private final SentenceSplitter sentenceSplitter = new SentenceSplitter();

    public FeatureConverter(StorynatorLogger logger, FieldMappingConfig config, SourceConfig sourceConfig) {
        super(config, sourceConfig);
        this.logger = logger;
    }

    public Feature convert(JiraJsonIssue jiraJsonIssue) {
        Feature feature = new Feature();
        // Extract description
        if (hasValidDescription(jiraJsonIssue)) {
            feature = sentenceSplitter.splitSentence(feature, jiraJsonIssue.getFields().getDescription());
        }

        feature.setSummary(jiraJsonIssue.getFields().getSummary());
        getAcceptanceCriteria(feature, jiraJsonIssue);

        Map<String, Object> additionalProps = jiraJsonIssue.getFields().getAdditionalProperties();
        feature.setEstimation(this.parseEstimation(additionalProps.getOrDefault(config.getFeature().getEstimation(), "").toString()));

        return (Feature) fillDefaultInfo(jiraJsonIssue, feature);
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{"story", "User Story"};
    }

    private boolean hasValidDescription(JiraJsonIssue jiraJsonIssue) {
        return StringUtils.isNotEmpty(jiraJsonIssue.getFields().getDescription());
    }

    private void getAcceptanceCriteria(Feature feature, JiraJsonIssue jiraJsonIssue) {
        String criteriaKey = config.getFeature().getAcceptanceCriteria();
        if (StringUtils.isNotEmpty(criteriaKey)
                && jiraJsonIssue.getFields().getAdditionalProperties().containsKey(criteriaKey)
                && StringUtils.isNotEmpty((String) jiraJsonIssue.getFields().getAdditionalProperties().get(criteriaKey))) {
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
