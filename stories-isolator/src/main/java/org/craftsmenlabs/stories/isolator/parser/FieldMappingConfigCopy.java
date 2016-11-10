package org.craftsmenlabs.stories.isolator.parser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldMappingConfigCopy {
    private BacklogMappingCopy backlog;
    private IssueMappingCopy issue;
    private StoryMappingCopy story;
    private CriteriaMappingCopy criteria;
    private EstimationMappingCopy estimation;

    @Data
    @Builder
    public static class IssueMappingCopy {
        private String rank;
        private String summary;
        private String estimation;
    }

    @Data
    @Builder
    public static class StoryMappingCopy {
    }

    @Data
    @Builder
    public static class BacklogMappingCopy {
    }

    @Data
    @Builder
    public static class CriteriaMappingCopy {
    }

    @Data
    @Builder
    public static class EstimationMappingCopy {
    }
}
