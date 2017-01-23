package org.craftsmenlabs.stories.importer.jira;

import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Importer
 */
public class JiraAPIImporter implements Importer {
    private final StorynatorLogger logger;

    private String urlResource;
    private String projectKey;
    private String username;
    private String password;

    private StorynatorConfig storynatorConfig;
    private JiraFieldMapRetriever jiraFieldMapRetriever;

    public JiraAPIImporter(StorynatorLogger logger, StorynatorConfig storynatorConfig) {
        this.logger = logger;
        SourceConfig.JiraConfig jiraConfig = storynatorConfig.getSource().getJira();
        this.urlResource = jiraConfig.getUrl();
        this.projectKey = jiraConfig.getProjectKey();
        this.username = jiraConfig.getUsername();
        this.password = jiraConfig.getPassword();

        this.storynatorConfig = storynatorConfig;

        jiraFieldMapRetriever = new JiraFieldMapRetriever(username, password, urlResource, logger);
    }

    @Override
    public Backlog getBacklog() {

        // build URL params
        String url = urlResource + "/rest/api/2/search";
        logger.info("Retrieving data from: " + url);

        RestTemplate restTemplate = new RestTemplate();

        JiraRequest jiraRequest = JiraRequest.builder()
                .jql("project=" + projectKey)
                .maxResults(10000)
                .build();
        try {
            // Add auth token
            restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
                request.getHeaders().add("Authorization", "Basic " + Base64Utils.encodeToString((this.username + ":" + this.password).getBytes()));
                return execution.execute(request, body);
            }));
            //get the jira backlog
            JiraBacklog backlog = restTemplate.postForObject(url, jiraRequest, JiraBacklog.class);

            //retrieve the human readable fieldmap
            final Map<String, String> fieldMap = jiraFieldMapRetriever.getFieldMap();
            final FieldMappingConfig fieldMappingConfig = this.mapToJiraIds(storynatorConfig.getFieldMapping(), fieldMap);

            //init the parser
            JiraJsonParser parser = new JiraJsonParser(
                    logger,
                    fieldMappingConfig,
                    storynatorConfig.getFilter(),
                    storynatorConfig.getSource()
            );

            //parse the jira backlog to a storynator backlog
            return parser.parse(backlog);
        } catch (HttpClientErrorException e) {
            logger.error("Jira call went wrong with url: " + urlResource + " and body: " + jiraRequest);
            throw new StoriesException("Failed to connect to " + url + " Error message was: " + e.getMessage() + "body: \r\n" + e.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new StoriesException("Unable to parse fields");
        }
    }

    /**
     * Maps the human readable fields which the user provides in the config to the field-id's
     */
    public FieldMappingConfig mapToJiraIds(FieldMappingConfig fieldMappingConfigByJiraNames, Map<String, String> fieldMap) throws IOException {
        FieldMappingConfig.FeatureMapping featureMapping = fieldMappingConfigByJiraNames.getFeature() == null ? new FieldMappingConfig.FeatureMapping() : fieldMappingConfigByJiraNames.getFeature();
        FieldMappingConfig.BugMapping bugMapping = fieldMappingConfigByJiraNames.getBug() == null ? new FieldMappingConfig.BugMapping() : fieldMappingConfigByJiraNames.getBug();
        FieldMappingConfig.EpicMapping epicMapping = fieldMappingConfigByJiraNames.getEpic() == null ? new FieldMappingConfig.EpicMapping() : fieldMappingConfigByJiraNames.getEpic();
        FieldMappingConfig.TeamTaskMapping teamTaskMapping = fieldMappingConfigByJiraNames.getTeamTask() == null ? new FieldMappingConfig.TeamTaskMapping() : fieldMappingConfigByJiraNames.getTeamTask();

        return FieldMappingConfig.builder()
                .feature(FieldMappingConfig.FeatureMapping.builder()
                        .estimation(getOrDefault(featureMapping.getEstimation(), fieldMap))
                        .acceptanceCriteria(getOrDefault(featureMapping.getAcceptanceCriteria(), fieldMap))
                        .build())
                .bug(FieldMappingConfig.BugMapping.builder()
                        .priority(getOrDefault(bugMapping.getPriority(), fieldMap))
                        .reproductionPath(getOrDefault(bugMapping.getReproductionPath(), fieldMap))
                        .software(getOrDefault(bugMapping.getSoftware(), fieldMap))
                        .expectedBehavior(getOrDefault(bugMapping.getExpectedBehavior(), fieldMap))
                        .acceptationCriteria(getOrDefault(bugMapping.getAcceptationCriteria(), fieldMap))
                        .build())
                .epic(new FieldMappingConfig.EpicMapping(getOrDefault(epicMapping.getGoal(), fieldMap)))
                .teamTask(FieldMappingConfig.TeamTaskMapping.builder()
                        .estimation(getOrDefault(teamTaskMapping.getEstimation(), fieldMap))
                        .acceptanceCriteria(getOrDefault(teamTaskMapping.getAcceptanceCriteria(), fieldMap))
                        .build())
                .rank(getOrDefault(fieldMappingConfigByJiraNames.getRank(), fieldMap))
                .build();
    }


    private String getOrDefault(String value, Map<String, String> fieldMap) {
        if(value == null || fieldMap == null){
            return "";
        }
        final String orDefault = fieldMap.getOrDefault(value, value);

        return orDefault;
    }
}
