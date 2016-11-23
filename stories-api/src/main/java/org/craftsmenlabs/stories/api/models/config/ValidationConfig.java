package org.craftsmenlabs.stories.api.models.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Data
    public static class ValidatorEntry {
        private float ratingtreshold;
        private boolean active;

        @Override
        public String toString() {
            return "config: {" +
                    "ratingtreshold=" + ratingtreshold +
                    ", active=" + active +
                    '}';
        }
    }

    @Data
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
    public static class BugValidatorEntry extends ValidatorEntry {
        private List<String> enabledFields;

        @Override
        public String toString() {
            return super.toString() +
                    " EnabledFields: {" + StringUtils.join(enabledFields, ", ") + "}";
        }
    }

    @Data
    public static class EpicValidatorEntry extends ValidatorEntry {
        private List<String> enabledFields;

        @Override
        public String toString() {
            return super.toString() +
                    " EnabledFields: {" + StringUtils.join(enabledFields, ", ") + "}";
        }
    }
}
