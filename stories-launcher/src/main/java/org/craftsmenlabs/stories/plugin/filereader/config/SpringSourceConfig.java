package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix =  "source", ignoreInvalidFields = true)
public class SpringSourceConfig {
    private String enabled = "jira";
    private JiraConfig jira;
    private TrelloConfig trello;
    private FileConfig file;

    @Data
    public static class JiraConfig {
        private String url;
        private String authKey;
        private String projectKey;
    }

    @Data
    public static class TrelloConfig {
        private String url;
        private String token;
        private String authKey;
        private String projectKey;
    }

    @Data
    public static class FileConfig {
        private String location;
    }
}
