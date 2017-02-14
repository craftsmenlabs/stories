package org.craftsmenlabs.stories.importer.jira;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JiraFieldMapRetriever {

    private String username;
    private String password;
    private String urlResource;
    private final StorynatorLogger logger;

    public JiraFieldMapRetriever(String username, String password, String urlResource, StorynatorLogger logger) {
        this.username = username;
        this.password = password;
        this.urlResource = urlResource;
        this.logger = logger;
    }

    /**
     * Retrieves the fields from {jira-instance.com}/rest/api/2/field and
     * returns a map from the human readable field to the jira field id
     *
     * @return
     * @throws IOException
     */
    public Map<String, String> getFieldMap() throws IOException {
        // build URL params
        String url = urlResource + "/rest/api/2/field";
        logger.info("Retrieving field mapping from: " + url);

        RestTemplate restTemplate = new RestTemplate();

        // Add auth token
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Basic " + Base64Utils.encodeToString((this.username + ":" + this.password).getBytes()));
            return execution.execute(request, body);
        }));

        final String responseEntity = restTemplate.getForObject(url, String.class);

        //if retrieving fails, continue with an empty fieldMapper
        if (responseEntity == null) {
            return new HashMap<>();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        final CollectionType valueType = objectMapper.getTypeFactory().constructCollectionType(List.class, JiraFieldMap.class);
        List<JiraFieldMap> jiraFieldMaps = objectMapper.readValue(responseEntity, valueType);

        return jiraFieldMaps.stream().collect(Collectors.toMap(JiraFieldMap::getName, JiraFieldMap::getId, (m1, m2) -> {
            logger.warn(String.format("Duplicate name found in jira field mapping, with keys %s and %s. Please ask your Jira admin to remove this ambiguity. I will default to %s", m1, m2, m2));
            return m2;
        }));
    }
}
