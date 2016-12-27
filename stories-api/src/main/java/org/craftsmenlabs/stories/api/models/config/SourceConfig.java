package org.craftsmenlabs.stories.api.models.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceConfig {
    private String type;
    private JiraConfig jira;
    private TrelloConfig trello;
    private FileConfig file;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JiraConfig {
        private String url;
        private String username;
        private String password;
        private String projectKey;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrelloConfig {
        private String url;
        private String token;
        private String authKey;
        private String projectKey;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileConfig {
        private String location;
    }
}
