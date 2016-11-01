package org.craftsmenlabs.stories.plugin.filereader;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix="validation")
public class ValidationConfig {
    private ValidatorEntry backlog;
    private ValidatorEntry issue;
    private StoryValidatorEntry story;
    private CriteriaValidatorEntry criteria;
    private ValidatorEntry estimation;

    public ValidationConfigCopy clone() {
        ValidationConfigCopy copy = new ValidationConfigCopy();

        copy.setBacklog(this.getBacklog().clone());
        copy.setIssue(this.getIssue().clone());
        copy.setStory(this.getStory().clone());
        copy.setCriteria(this.getCriteria().clone());
        copy.setEstimation(this.getEstimation().clone());

        return copy;
    }

    @Data
    public static class ValidatorEntry {
        private float ratingtreshold;
        private boolean active;

        public ValidationConfigCopy.ValidatorEntryCopy clone() {
            ValidationConfigCopy.ValidatorEntryCopy validatorEntryCopy = new ValidationConfigCopy.ValidatorEntryCopy();

            validatorEntryCopy.setRatingtreshold(getRatingtreshold());
            validatorEntryCopy.setActive(isActive());

            return validatorEntryCopy;
        }
    }

    @Data
    public static class StoryValidatorEntry extends ValidatorEntry {
        private List<String> asKeywords;
        private List<String> iKeywords;
        private List<String> soKeywords;

        public ValidationConfigCopy.StoryValidatorEntryCopy clone() {
            ValidationConfigCopy.StoryValidatorEntryCopy sve = new ValidationConfigCopy.StoryValidatorEntryCopy();
            sve.setRatingtreshold(getRatingtreshold());
            sve.setActive(isActive());

            sve.setAsKeywords(getAsKeywords());
            sve.setIKeywords(getIKeywords());
            sve.setSoKeywords(getSoKeywords());

            return sve;
        }
    }

    @Data
    public static class CriteriaValidatorEntry extends ValidatorEntry {
        private List<String> givenKeywords;
        private List<String> whenKeywords;
        private List<String> thenKeywords;

        public ValidationConfigCopy.CriteriaValidatorEntryCopy clone() {
            ValidationConfigCopy.CriteriaValidatorEntryCopy sve = new ValidationConfigCopy.CriteriaValidatorEntryCopy();
            sve.setRatingtreshold(getRatingtreshold());
            sve.setActive(isActive());

            sve.setGivenKeywords(getGivenKeywords());
            sve.setWhenKeywords(getWhenKeywords());
            sve.setThenKeywords(getThenKeywords());

            return sve;
        }
    }
}
