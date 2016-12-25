package org.craftsmenlabs.stories.connectivity.service.enterprise;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.springframework.web.client.RestTemplate;

public class EnterpriseDashboardConfigRetriever {
    public static ConfigMapping retrieveSettings(String baseUrl, String token) {

        try {
            RestTemplate template = new RestTemplate();
            String url = baseUrl + "/api/import/v1/config/" + token;
            return template.getForObject(url, ConfigMapping.class);

        } catch (Exception e) {
            throw new StoriesException("Could not retrieve settings for the project: " + e.getMessage());
        }
    }

    @Data
    @NoArgsConstructor
    public static class ConfigMapping {
        private FilterConfig filterConfig;
        private FieldMappingConfig fieldMappingConfig;
        private ValidationConfig validationConfig;
    }
}
