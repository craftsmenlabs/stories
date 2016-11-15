package org.craftsmenlabs.stories.plugin.filereader;

import lombok.Data;
import org.craftsmenlabs.stories.isolator.parser.FieldMappingConfigCopy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fieldMapping", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class FieldMappingConfig {
    private IssueMapping issue;

    public FieldMappingConfigCopy clone() {
        return FieldMappingConfigCopy.builder()
                .issue(this.getIssue().clone())
                .build();
    }

    @Data
    public static class IssueMapping {
        private String rank;
        private String estimation;

        public FieldMappingConfigCopy.IssueMappingCopy clone() {
            if (rank == null || rank.isEmpty()) {
                throw new IllegalStateException("The rank attribute has not been set correctly.");
            }

            return FieldMappingConfigCopy.IssueMappingCopy.builder()
                    .rank(rank)
                    .estimation(estimation)
                    .build();
        }
    }

}
