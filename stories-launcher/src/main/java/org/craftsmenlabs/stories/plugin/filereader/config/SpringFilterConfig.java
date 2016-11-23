package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.FilterConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "filters")
public class SpringFilterConfig {
    private String status;

    public FilterConfig convert() {
        return FilterConfig.builder()
                .status(status)
                .build();
    }
}
