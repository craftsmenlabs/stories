package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fieldMapping")
public class SpringFieldMappingConfig implements ValidatableConfig {
    private FeatureMapping feature;
    private BugMapping bug;
    private EpicMapping epic;
    private String rank;

    public FieldMappingConfig convert() {
        return FieldMappingConfig.builder()
                .feature(this.getFeature().convert())
                .bug(this.getBug() != null ? this.getBug().convert() : null)
                .epic(this.getEpic() != null ? this.epic.convert() : null)
                .rank(rank)
                .build();
    }

    @Override
    public void validate() throws StoriesException {
        if (rank == null || rank.isEmpty()) {
            throw new IllegalStateException("The rank attribute has not been set correctly.");
        }
    }

    @Data
    public static class FeatureMapping {
        private String estimation;
        private String acceptanceCriteria;

        public FieldMappingConfig.FeatureMapping convert() {
            return FieldMappingConfig.FeatureMapping.builder()
                    .estimation(estimation)
                    .acceptanceCriteria(acceptanceCriteria)
                    .build();
        }
    }

    @Data
    public static class BugMapping {
        private String reproductionPath;
        private String software;
        private String expectedBehavior;
        private String acceptationCriteria;

        public FieldMappingConfig.BugMapping convert() {
            return FieldMappingConfig.BugMapping.builder()
                    .reproductionPath(reproductionPath)
                    .software(software)
                    .expectedBehavior(expectedBehavior)
                    .acceptationCriteria(acceptationCriteria)
                    .build();
        }
    }

    @Data
    public static class EpicMapping {
        private String goal;

        public FieldMappingConfig.EpicMapping convert() {
            return FieldMappingConfig.EpicMapping.builder()
                    .goal(goal)
                    .build();
        }
    }

}
