package org.craftsmenlabs.stories.api.models.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private GithubConfig github;
    private FileConfig file;

    public static SourceConfig createDefault() {
        return SourceConfig.builder()
                .type("jira")
                .trello(SourceConfig.TrelloConfig.builder().build())
                .jira(SourceConfig.JiraConfig.builder().build())
                .github(SourceConfig.GithubConfig.builder().build())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JiraConfig {
        private String url;
        private String username;
        @JsonIgnore
        private String password;
        private String projectKey;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrelloConfig {
        private String token;
        @JsonIgnore
        private String authKey;
        private String projectKey;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GithubConfig {
        @JsonIgnore
        private String token;
        private String owner;
        private String project;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileConfig {
        private String location;
    }
}
