package org.craftsmenlabs.stories.connectivity.service.enterprise;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.springframework.web.client.RestTemplate;

public class EnterpriseDashboardConfigRetriever {
    private RestTemplate restTemplate = new RestTemplate();

    public StorynatorConfig retrieveSettings(String baseUrl, String token, String password) throws Exception {

        String url = baseUrl + "/api/import/v1/config/" + token;
        StorynatorConfig storynatorConfig = restTemplate.getForObject(url, StorynatorConfig.class);

        // Set auth tokens if needed
        SourceConfig source = storynatorConfig.getSource();
        if (StringUtils.equalsIgnoreCase("jira", source.getType()) && StringUtils.isNotEmpty(password)) {
            source.getJira().setPassword(password);
        }

        return storynatorConfig;

    }
}
