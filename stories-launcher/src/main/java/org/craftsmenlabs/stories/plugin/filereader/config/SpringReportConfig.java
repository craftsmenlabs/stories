package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "report")
public class SpringReportConfig implements ValidatableConfig {
    private DashboardConfig dashboard;
    private FileConfig file;

    @Override
    public void validate() throws StoriesException {
        // Cascade
        this.file.validate();
        this.dashboard.validate();
    }

    @Data
    public static class DashboardConfig implements ValidatableConfig {
        private boolean enabled = false;
        private String url;
        private String token;

        @Override
        public void validate() throws StoriesException {
            if(this.enabled && (StringUtils.isEmpty(url) || StringUtils.isEmpty(token)) ) {
                throw new StoriesException("When dashboard reporting is enabled, please provide a dashboard url and token.");
            }
        }
    }

    @Data
    public static class FileConfig implements ValidatableConfig{
        private boolean enabled = false;
        private String location;

        @Override
        public void validate() throws StoriesException {
            if(this.enabled && (StringUtils.isEmpty(location))) {
                throw new StoriesException("When JSON output file reporting is enabled the output location should be provided!");
            }
        }
    }
}
