package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "report")
public class SpringReportConfig {
    private DashboardConfig dashboard;
    private FileConfig file;

    @Data
    public static class DashboardConfig {
        private boolean enabled;
        private String url;
        private String token;
    }

    @Data
    public static class FileConfig {
        private boolean enabled;
        private String location;
    }
}
