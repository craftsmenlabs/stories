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


    public static FieldMappingConfig createDefault() {
        return FieldMappingConfig.builder()
                .rank("")
                .feature(FieldMappingConfig.FeatureMapping.builder().build())
                .bug(FieldMappingConfig.BugMapping.builder().build())
                .epic(FieldMappingConfig.EpicMapping.builder().build())
                .teamTask(FieldMappingConfig.TeamTaskMapping.builder().build())
                .build();
    }

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
        private String environment;
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
