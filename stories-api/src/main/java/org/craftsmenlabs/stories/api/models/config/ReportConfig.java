package org.craftsmenlabs.stories.api.models.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportConfig {
    private DashboardConfig dashboard;
    private FileConfig file;

    @Data
    @Builder
    public static class DashboardConfig {
        private boolean enabled = false;
        private String url;
        private String token;
    }

    @Data
    @Builder
    public static class FileConfig {
        private boolean enabled = false;
        private String location;
    }
}
