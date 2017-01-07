package org.craftsmenlabs.stories.launcher.config;

import lombok.AllArgsConstructor;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@ConfigurationProperties(prefix = "storynator")
public class SpringStorynatorConfig extends StorynatorConfig {
}
