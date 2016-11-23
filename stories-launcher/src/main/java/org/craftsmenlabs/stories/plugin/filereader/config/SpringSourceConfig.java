package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix =  "source", ignoreInvalidFields = true)
public class SpringSourceConfig implements ValidatableConfig {
    private String type;
    private JiraConfig jira;
    private TrelloConfig trello;
    private FileConfig file;

    @Override
    public void validate() throws StoriesException {
        if (StringUtils.equals(this.type, "jira")) {
            this.jira.validate();
        }
        if (StringUtils.equals(this.type, "trello")) {
            this.trello.validate();
        }
    }

    @Data
    public static class JiraConfig implements ValidatableConfig {
        private String url;
        private String authKey;
        private String projectKey;

        @Override
        public void validate() throws StoriesException {
            if(StringUtils.isEmpty(url) || StringUtils.isEmpty(authKey) || StringUtils.isEmpty(projectKey)) {
                throw new StoriesException("Your JIRA configuration is invalid. It should have the url, authKey and projectKey defined. Please refer to the documentation");
            }
        }
    }

    @Data
    public static class TrelloConfig implements ValidatableConfig {
        private String url;
        private String token;
        private String authKey;
        private String projectKey;

        @Override
        public void validate() throws StoriesException {

            if(StringUtils.isEmpty(url) || StringUtils.isEmpty(token) || StringUtils.isEmpty(authKey) || StringUtils.isEmpty(projectKey)) {
                throw new StoriesException("Your Trello configuration is invalid. It should have the url, authKey, token and projectKey defined. Please refer to the documentation");
            }
        }
    }

    @Data
    public static class FileConfig {
        private String location;
    }
}
