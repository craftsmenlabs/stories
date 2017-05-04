package org.craftsmenlabs.stories.api.models.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
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

    public static ValidationConfig createDefault() {
        return  ValidationConfig.builder()
                .backlog(new ValidationConfig.ValidatorEntry(60.0, true))
                .feature(new ValidationConfig.ValidatorEntry(60.0, true))
                .bug(BugValidatorEntry.createDefault())
                .criteria(CriteriaValidatorEntry.createDefault())
                .estimation(new ValidationConfig.ValidatorEntry(60.0, true))
                .epic(EpicValidatorEntry.createDefault())
                .story(StoryValidatorEntry.createDefault())
                .teamTask(new ValidationConfig.ValidatorEntry(60.0, true))
                .build();
    }

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

        public static StoryValidatorEntry createDefault() {
            StoryValidatorEntry entry = new StoryValidatorEntry();
            entry.setActive(true);
            entry.setRatingThreshold(60.0);
            entry.setAsKeywords(Collections.singletonList("As a"));
            entry.setIKeywords(Collections.singletonList("I want"));
            entry.setSoKeywords(Collections.singletonList("So that"));
            return entry;
        }

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

        public static CriteriaValidatorEntry createDefault() {
            CriteriaValidatorEntry entry = new CriteriaValidatorEntry();
            entry.setActive(true);
            entry.setRatingThreshold(60.0);
            entry.setGivenKeywords(Collections.singletonList("given"));
            entry.setWhenKeywords(Collections.singletonList("when"));
            entry.setThenKeywords(Collections.singletonList("then"));
            return entry;
        }

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
        public static BugValidatorEntry createDefault() {
            BugValidatorEntry entry = new BugValidatorEntry();
            entry.setActive(true);
            entry.setRatingThreshold(60.0);
            entry.setEnabledFields(Arrays.asList("priority", "reproduction_path", "environment", "expected_behaviour", "acceptation_criteria"));
            return entry;
        }
    }

    @Data
    @NoArgsConstructor
    @ToString(callSuper = true)
    public static class EpicValidatorEntry extends FillableValidatorEntry {
        public static EpicValidatorEntry createDefault() {
            EpicValidatorEntry entry = new EpicValidatorEntry();
            entry.setActive(true);
            entry.setRatingThreshold(60.0);
            entry.setEnabledFields(Collections.singletonList("goal"));
            return entry;
        }
    }
}
