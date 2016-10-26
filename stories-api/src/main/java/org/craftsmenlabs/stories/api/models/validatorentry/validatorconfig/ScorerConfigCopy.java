package org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig;

import lombok.Data;

import java.util.List;

@Data
public class ScorerConfigCopy {
    private ValidatorEntryCopy backlog;
    private ValidatorEntryCopy issue;
    private StoryValidatorEntryCopy story;
    private CriteriaValidatorEntryCopy criteria;
    private ValidatorEntryCopy estimation;

    @Data
    public static class ValidatorEntryCopy {
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
