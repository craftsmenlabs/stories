package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.base.TeamTask;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;

import java.util.Map;

public class TeamTaskConverter extends AbstractJiraConverter<TeamTask> {
    private final StorynatorLogger logger;

    public TeamTaskConverter(StorynatorLogger logger, FieldMappingConfig config, SourceConfig sourceConfig) {
        super(config, sourceConfig);
        this.logger = logger;
    }

    public TeamTask convert(JiraJsonIssue jiraJsonIssue) {
        TeamTask teamTask = TeamTask.empty();
        teamTask.setSummary(jiraJsonIssue.getFields().getSummary());

        teamTask.setDescription(jiraJsonIssue.getFields().getDescription());
        getAcceptanceCriteria(teamTask, jiraJsonIssue);

        Map<String, Object> additionalProps = jiraJsonIssue.getFields().getAdditionalProperties();

        String criteria = (String) additionalProps.get(config.getTeamTask().getAcceptanceCriteria());
        teamTask.setAcceptationCriteria(criteria);

        Object estimation = additionalProps.getOrDefault(config.getFeature().getEstimation(), "");
        if (estimation instanceof String) {
            teamTask.setEstimation(this.parseEstimation((String) estimation));
        } else {
            teamTask.setEstimation((Float) estimation);
        }
        return (TeamTask) fillDefaultInfo(jiraJsonIssue, teamTask);
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{"Task", "Team task"};
    }

    private void getAcceptanceCriteria(TeamTask teamTask, JiraJsonIssue jiraJsonIssue) {
        String criteriaKey = config.getTeamTask().getAcceptanceCriteria();
        if (StringUtils.isNotEmpty(criteriaKey)
                && jiraJsonIssue.getFields().getAdditionalProperties().containsKey(criteriaKey)
                && StringUtils.isNotEmpty((String) jiraJsonIssue.getFields().getAdditionalProperties().get(criteriaKey))) {
            // We should get the acceptance Criteria from this field
            teamTask.setAcceptationCriteria((String) jiraJsonIssue.getFields().getAdditionalProperties().get(criteriaKey));
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
