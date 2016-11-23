package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.config.ReportConfig;
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
        if(file != null) {
            this.file.validate();
        }
        if(dashboard != null) {
            this.dashboard.validate();
        }
    }

    public ReportConfig convert() {
        final ReportConfig.FileConfig fileConfig =
                (file != null) ?
                        ReportConfig.FileConfig.builder()
                            .enabled(file.enabled)
                            .location(file.location)
                            .build() :
                        null;



        final ReportConfig.DashboardConfig dashboardConfig =
                dashboard != null ?
                        ReportConfig.DashboardConfig.builder()
                            .enabled(dashboard.enabled)
                            .token(dashboard.token)
                            .url(dashboard.url)
                            .build() :
                null;

        return ReportConfig.builder()
                .dashboard(dashboardConfig)
                .file(fileConfig)
                .build();
    }

    @Data
    public static class DashboardConfig implements ValidatableConfig {
        private boolean enabled = false;
        private String url;
        private String token;

        @Override
        public void validate() throws StoriesException {
            if(this.enabled && (StringUtils.isEmpty(url) || StringUtils.isEmpty(token)) ) {
                throw new StoriesException("When dashboard reporting is type, please provide a dashboard url and token.");
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
                throw new StoriesException("When JSON output file reporting is type the output location should be provided!");
            }
        }
    }
}
