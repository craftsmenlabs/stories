package org.craftsmenlabs.stories.api.models.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldMappingConfig {
    private BacklogMapping backlog;
    private FeatureMapping feature;
    private BugMapping bug;
    private EpicMapping epic;
    private TeamTaskMapping teamTask;
    private String rank;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureMapping {
        private String estimation;
        private String acceptanceCriteria;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamTaskMapping {
        private String estimation;
        private String acceptanceCriteria;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BugMapping {
        private String priority;
        private String reproductionPath;
        private String software;
        private String expectedBehavior;
        private String acceptationCriteria;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EpicMapping {
        private String goal;
    }

    @Data
    @Builder
    public static class BacklogMapping {
    }
}
