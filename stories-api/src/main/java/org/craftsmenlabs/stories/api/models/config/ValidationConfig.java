package org.craftsmenlabs.stories.api.models.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationConfig {
    @JsonProperty("backlog")
    private ValidatorEntry backlog;

    @JsonProperty("feature")
    private ValidatorEntry feature;

    @JsonProperty("story")
    private StoryValidatorEntry story;

    @JsonProperty("criteria")
    private CriteriaValidatorEntry criteria;

    @JsonProperty("estimation")
    private ValidatorEntry estimation;

    @JsonProperty("bug")
    private BugValidatorEntry bug;

    @JsonProperty("epic")
    private EpicValidatorEntry epic;

    @JsonProperty("teamTask")
    private ValidatorEntry teamTask;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidatorEntry {
        private double ratingThreshold;
        private boolean active;

        @Override
        public String toString() {
            return "config: {" +
                    "ratingThreshold=" + ratingThreshold +
                    ", active=" + active +
                    '}';
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FillableValidatorEntry extends ValidatorEntry {
        private List<String> enabledFields;

        @Override
        public String toString() {
            return super.toString() +
                    " EnabledFields: {" + StringUtils.join(enabledFields, ", ") + "}";
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoryValidatorEntry extends ValidatorEntry {
        private List<String> asKeywords;
        private List<String> iKeywords;
        private List<String> soKeywords;

        @Override
        public String toString() {
            return super.toString() +
                    " Keywords{" +
                    "asKeywords=" + asKeywords +
                    ", iKeywords=" + iKeywords +
                    ", soKeywords=" + soKeywords +
                    '}';
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CriteriaValidatorEntry extends ValidatorEntry {
        private List<String> givenKeywords;
        private List<String> whenKeywords;
        private List<String> thenKeywords;

        @Override
        public String toString() {
            return super.toString() +
                    " Keywords: {" +
                    "givenKeywords=" + givenKeywords +
                    ", whenKeywords=" + whenKeywords +
                    ", thenKeywords=" + thenKeywords +
                    '}';
        }
    }

    @Data
    @NoArgsConstructor
    @ToString(callSuper = true)
    public static class BugValidatorEntry extends FillableValidatorEntry {

    }

    @Data
    @NoArgsConstructor
    @ToString(callSuper = true)
    public static class EpicValidatorEntry extends FillableValidatorEntry {
    }
}
