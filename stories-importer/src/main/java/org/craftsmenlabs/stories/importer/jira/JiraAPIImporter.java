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

//    private Map<String, String> fieldMap;

    public JiraAPIImporter(StorynatorLogger logger, StorynatorConfig storynatorConfig) {
        this.logger = logger;
        SourceConfig.JiraConfig jiraConfig = storynatorConfig.getSource().getJira();
        this.urlResource = jiraConfig.getUrl();
        this.projectKey = jiraConfig.getProjectKey();
        this.username = jiraConfig.getUsername();
        this.password = jiraConfig.getPassword();

        this.storynatorConfig = storynatorConfig;
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
            final Map<String, String> fieldMap = new JiraFieldMapRetriever(username, password, urlResource, logger).getFieldMap();
            final FieldMappingConfig fieldMappingConfig = this.mapToHumanReadableFields(storynatorConfig.getFieldMapping(), fieldMap);

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
    public FieldMappingConfig mapToHumanReadableFields(FieldMappingConfig nonHumanReadableFieldMappingConfig, Map<String, String> fieldMap) throws IOException {
        FieldMappingConfig.FeatureMapping featureMapping = nonHumanReadableFieldMappingConfig.getFeature() == null ? new FieldMappingConfig.FeatureMapping() : nonHumanReadableFieldMappingConfig.getFeature();
        FieldMappingConfig.BugMapping bugMapping = nonHumanReadableFieldMappingConfig.getBug() == null ? new FieldMappingConfig.BugMapping() : nonHumanReadableFieldMappingConfig.getBug();
        FieldMappingConfig.EpicMapping epicMapping = nonHumanReadableFieldMappingConfig.getEpic() == null ? new FieldMappingConfig.EpicMapping() : nonHumanReadableFieldMappingConfig.getEpic();
        FieldMappingConfig.TeamTaskMapping teamTaskMapping = nonHumanReadableFieldMappingConfig.getTeamTask() == null ? new FieldMappingConfig.TeamTaskMapping() : nonHumanReadableFieldMappingConfig.getTeamTask();

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
                .rank(getOrDefault(nonHumanReadableFieldMappingConfig.getRank(), fieldMap))
                .build();
    }


    private String getOrDefault(String estimation, Map<String, String> fieldMap) {
        if(estimation == null || fieldMap == null){
            return "";
        }
        return fieldMap.getOrDefault(estimation.toLowerCase().replace(" ", ""), estimation);
    }
}
