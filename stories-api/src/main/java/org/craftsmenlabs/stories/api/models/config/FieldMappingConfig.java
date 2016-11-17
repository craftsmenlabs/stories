package org.craftsmenlabs.stories.api.models.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldMappingConfig {
    private BacklogMapping backlog;
    private IssueMapping issue;
    private StoryMapping story;
    private CriteriaMapping criteria;
    private EstimationMapping estimation;

    @Data
    @Builder
    public static class IssueMapping {
        private String rank;
        private String estimation;
        private String acceptenceCriteria;
    }

    @Data
    @Builder
    public static class StoryMapping {
    }

    @Data
    @Builder
    public static class BacklogMapping {
    }

    @Data
    @Builder
    public static class CriteriaMapping {
    }

    @Data
    @Builder
    public static class EstimationMapping {
    }
}
