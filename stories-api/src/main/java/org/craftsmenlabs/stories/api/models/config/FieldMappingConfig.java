package org.craftsmenlabs.stories.api.models.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldMappingConfig {
    private BacklogMapping backlog;
    private FeatureMapping feature;
    private BugMapping bug;
    private EpicMapping epic;
    private String rank;

    @Data
    @Builder
    public static class FeatureMapping {
        private String estimation;
        private String acceptanceCriteria;
    }

    @Data
    @Builder
    public static class BugMapping {
        private String priority;
        private String reproductionPath;
        private String software;
        private String expectedBehavior;
        private String acceptationCriteria;
    }

    @Data
    @Builder
    public static class EpicMapping {
        private String goal;
    }

    @Data
    @Builder
    public static class BacklogMapping {
    }
}
