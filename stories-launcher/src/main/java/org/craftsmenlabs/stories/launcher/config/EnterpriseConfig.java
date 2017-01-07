package org.craftsmenlabs.stories.launcher.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "enterprise")
public class EnterpriseConfig {
    private boolean enabled;
    private String url;
    private String token;
    private String password;
}
