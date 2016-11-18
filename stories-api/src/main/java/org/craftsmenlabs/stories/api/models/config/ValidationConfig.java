package org.craftsmenlabs.stories.api.models.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationConfig {
    @JsonProperty("backlog")
    private ValidatorEntry backlog;

    @JsonProperty("feature")
    private ValidatorEntry issue;

    @JsonProperty("story")
    private StoryValidatorEntry story;

    @JsonProperty("criteria")
    private CriteriaValidatorEntry criteria;

    @JsonProperty("estimation")
    private ValidatorEntry estimation;

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
}
