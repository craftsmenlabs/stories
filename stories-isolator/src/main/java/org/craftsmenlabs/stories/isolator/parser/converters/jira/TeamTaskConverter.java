package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.TeamTask;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TeamTaskConverter extends AbstractJiraConverter<TeamTask> {
    private final Logger logger = LoggerFactory.getLogger(TeamTaskConverter.class);

    public TeamTaskConverter(FieldMappingConfig config, SourceConfig sourceConfig) {
        super(config, sourceConfig);
    }

    public TeamTask convert(JiraJsonIssue jiraJsonIssue) {
        TeamTask teamTask = new TeamTask();

        teamTask.setKey(jiraJsonIssue.getKey());
        teamTask.setSummary(jiraJsonIssue.getFields().getSummary());
        teamTask.setExternalURI(jiraJsonIssue.getSelf());
        teamTask.setDescription(jiraJsonIssue.getFields().getDescription());
        getAcceptanceCriteria(teamTask, jiraJsonIssue);

        Map<String, Object> additionalProps = jiraJsonIssue.getFields().getAdditionalProperties();

        String rank = (String) additionalProps.get(config.getRank());
        if (StringUtils.isEmpty(rank)) {
            throw new StoriesException(
                    "The rank field mapping was not defined in your application yaml or parameters. " +
                            "Is the field mapping configured correctly?");
        }
        teamTask.setRank(rank);

        String criteria = (String) additionalProps.get(config.getTeamTask().getAcceptanceCriteria());
        teamTask.setAcceptationCriteria(criteria);

        teamTask.setEstimation(this.parseEstimation(additionalProps.getOrDefault(config.getFeature().getEstimation(), "").toString()));

        return teamTask;
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{"Task", "Team task"};
    }

    private boolean hasValidDescription(JiraJsonIssue jiraJsonIssue) {
        return StringUtils.isNotEmpty(jiraJsonIssue.getFields().getDescription());
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
