package org.craftsmenlabs.stories.plugin.filereader;

import lombok.RequiredArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardConfigRetriever;
import org.craftsmenlabs.stories.plugin.filereader.config.EnterpriseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StandaloneExecutor {
    private final EnterpriseConfig enterpriseConfig;
    private final StorynatorConfig storynatorConfig;
    private final Environment environment;
    private final Executor executor;

    public Rating runApplication() {
        StorynatorConfig config = this.resolveConfig();
        StorynatorVersion version = this.resolveVersion();
        StorynatorLogger logger = new StandaloneLogger();

        return executor.runApplication(config, version, logger);

    }

    private StorynatorVersion resolveVersion() {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        if (profiles.contains("enterprise") && !profiles.contains("community")) {
            return StorynatorVersion.ENTERPRISE;
        }
        return StorynatorVersion.COMMUNITY;
    }

    private StorynatorConfig resolveConfig() {
        if (enterpriseConfig.isEnabled()) {
            try {
                EnterpriseDashboardConfigRetriever dashboardConfigRetriever = new EnterpriseDashboardConfigRetriever();
                return dashboardConfigRetriever.retrieveSettings(enterpriseConfig.getUrl(), enterpriseConfig.getToken(), enterpriseConfig.getPassword());

            } catch (Exception e) {
                throw new StoriesException("Could not retrieve settings for the project: " + e.getMessage());
            }
        }
        return storynatorConfig;
    }
}
