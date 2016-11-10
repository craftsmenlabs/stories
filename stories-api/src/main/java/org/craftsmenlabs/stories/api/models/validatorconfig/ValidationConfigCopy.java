package org.craftsmenlabs.stories.api.models.validatorconfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ValidationConfigCopy {
    @JsonProperty("backlog")
    private ValidatorEntryCopy backlog;

    @JsonProperty("issue")
    private ValidatorEntryCopy issue;

    @JsonProperty("story")
    private StoryValidatorEntryCopy story;

    @JsonProperty("criteria")
    private CriteriaValidatorEntryCopy criteria;

    @JsonProperty("estimation")
    private ValidatorEntryCopy estimation;

    @Data
    public static class ValidatorEntryCopy {
        private float ratingtreshold;
        private boolean active;
        private Map<String, String> jsonFieldMapping;

        @Override
        public String toString() {
            return "config: {" +
                    "ratingtreshold=" + ratingtreshold +
                    ", active=" + active +
                    '}';
        }
    }

    @Data
    public static class StoryValidatorEntryCopy extends ValidatorEntryCopy {
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
    public static class CriteriaValidatorEntryCopy extends ValidatorEntryCopy {
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
