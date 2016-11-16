package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "field_mapping", ignoreInvalidFields = true)
public class SpringFieldMappingConfig {
    private IssueMapping issue;

    public FieldMappingConfig convert() {
        return FieldMappingConfig.builder()
                .issue(this.getIssue().convert())
                .build();
    }

    @Data
    public static class IssueMapping {
        private String rank;
        private String estimation;

        public FieldMappingConfig.IssueMapping convert() {
            if (rank == null || rank.isEmpty()) {
                throw new IllegalStateException("The rank attribute has not been set correctly.");
            }

            return FieldMappingConfig.IssueMapping.builder()
                    .rank(rank)
                    .estimation(estimation)
                    .build();
        }
    }

}
