package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "field_mapping", ignoreInvalidFields = true)
public class SpringFieldMappingConfig implements ValidatableConfig {
    private IssueMapping issue;

    public FieldMappingConfig convert() {
        return FieldMappingConfig.builder()
                .issue(this.getIssue().convert())
                .build();
    }

    @Override
    public void validate() throws StoriesException {
        // Cascade to children
        this.issue.validate();
    }

    @Data
    public static class IssueMapping implements ValidatableConfig {
        private String rank;
        private String estimation;
        private String acceptanceCriteria;

        public FieldMappingConfig.IssueMapping convert() {
            return FieldMappingConfig.IssueMapping.builder()
                    .rank(rank)
                    .estimation(estimation)
                    .acceptenceCriteria(acceptanceCriteria)
                    .build();
        }

        @Override
        public void validate() throws StoriesException {
            if (rank == null || rank.isEmpty()) {
                throw new IllegalStateException("The rank attribute has not been set correctly.");
            }
        }
    }

}
