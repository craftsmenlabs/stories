package org.craftsmenlabs.stories.importer.jira;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JiraFieldMapRetriever {
    private final StorynatorLogger logger;
    private RestTemplate restTemplate;

    public JiraFieldMapRetriever(StorynatorLogger logger) {
        this.logger = logger;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Retrieves the fields from {jira-instance.com}/rest/api/2/field and
     * returns a map from the human readable field to the jira field id
     *
     * @return
     * @throws IOException
     */
    public Map<String, String> getFieldMap(String username, String password, String urlResource) throws IOException {
        return retrieveFieldList(username, password, urlResource).stream().collect(Collectors.toMap(JiraFieldMap::getName, JiraFieldMap::getId, (m1, m2) -> {
            logger.warn(String.format("Duplicate name found in jira field mapping, with keys %s and %s. Please ask your Jira admin to remove this ambiguity. I will default to %s", m1, m2, m2));
            return m2;
        }));
    }

    public List<JiraFieldMap> retrieveFieldList(String username, String password, String urlResource) throws IOException {
        // build URL params
        String url = urlResource + "/rest/api/2/field";
        logger.info("Retrieving field mapping from: " + url);

        // Add auth token
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Basic " + Base64Utils.encodeToString((username + ":" + password).getBytes()));
            return execution.execute(request, body);
        }));

        final String responseEntity = restTemplate.getForObject(url, String.class);

        //if retrieving fails, continue with an empty fieldMapper
        if (responseEntity == null) {
            return Collections.emptyList();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        final CollectionType valueType = objectMapper.getTypeFactory().constructCollectionType(List.class, JiraFieldMap.class);
        return objectMapper.readValue(responseEntity, valueType);
    }
}
