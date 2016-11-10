package org.craftsmenlabs.stories.plugin.filereader;

import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.isolator.parser.FieldMappingConfigCopy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fieldMapping", ignoreInvalidFields = true, ignoreUnknownFields = true, ignoreNestedProperties = true)
public class FieldMappingConfig {
    private BacklogMapping backlog;
    private IssueMapping issue;
    private StoryMapping story;
    private CriteriaMapping criteria;
    private EstimationMapping estimation;

    public FieldMappingConfigCopy clone() {
        return FieldMappingConfigCopy.builder()
                .backlog(this.getBacklog().clone())
                .issue(this.getIssue().clone())
                .story(this.getStory().clone())
                .criteria(this.getCriteria().clone())
                .estimation(this.getEstimation().clone())
                .build();
    }


    @Data
    @Builder
    private static class BacklogMapping {
        public FieldMappingConfigCopy.BacklogMappingCopy clone() {
            return FieldMappingConfigCopy.BacklogMappingCopy.builder().build();
        }
    }

    @Data
    @Builder
    private static class IssueMapping {
        private String rank;
        private String summary;
        private String estimation;

        public FieldMappingConfigCopy.IssueMappingCopy clone() {
            return FieldMappingConfigCopy.IssueMappingCopy.builder()
                    .rank(rank)
                    .summary(summary)
                    .estimation(estimation)
                    .build();
        }
    }

    @Data
    @Builder
    private static class StoryMapping {
        public FieldMappingConfigCopy.StoryMappingCopy clone() {
            return FieldMappingConfigCopy.StoryMappingCopy.builder().build();
        }
    }

    @Data
    @Builder
    private static class CriteriaMapping {
        public FieldMappingConfigCopy.CriteriaMappingCopy clone() {
            return FieldMappingConfigCopy.CriteriaMappingCopy.builder().build();
        }
    }

    @Data
    @Builder
    private static class EstimationMapping {
        public FieldMappingConfigCopy.EstimationMappingCopy clone() {
            return FieldMappingConfigCopy.EstimationMappingCopy.builder().build();
        }
    }
}
