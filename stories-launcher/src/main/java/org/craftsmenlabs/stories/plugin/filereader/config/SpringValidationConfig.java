package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix="validation")
public class SpringValidationConfig {
    private ValidatorEntry backlog;
    private ValidatorEntry issue;
    private StoryValidatorEntry story;
    private CriteriaValidatorEntry criteria;
    private ValidatorEntry estimation;

    public ValidationConfig convert() {
        ValidationConfig validationConfig = new ValidationConfig();

        validationConfig.setBacklog(this.getBacklog().convert());
        validationConfig.setIssue(this.getIssue().convert());
        validationConfig.setStory(this.getStory().convert());
        validationConfig.setCriteria(this.getCriteria().convert());
        validationConfig.setEstimation(this.getEstimation().convert());

        return validationConfig;
    }

    @Data
    public static class ValidatorEntry {
        private float ratingtreshold;
        private boolean active;

        public ValidationConfig.ValidatorEntry convert() {
            ValidationConfig.ValidatorEntry validatorEntry = new ValidationConfig.ValidatorEntry();

            validatorEntry.setRatingtreshold(getRatingtreshold());
            validatorEntry.setActive(isActive());

            return validatorEntry;
        }
    }

    @Data
    public static class StoryValidatorEntry extends ValidatorEntry {
        private List<String> asKeywords;
        private List<String> iKeywords;
        private List<String> soKeywords;

        public ValidationConfig.StoryValidatorEntry convert() {
            ValidationConfig.StoryValidatorEntry sve = new ValidationConfig.StoryValidatorEntry();
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

        public ValidationConfig.CriteriaValidatorEntry convert() {
            ValidationConfig.CriteriaValidatorEntry sve = new ValidationConfig.CriteriaValidatorEntry();
            sve.setRatingtreshold(getRatingtreshold());
            sve.setActive(isActive());

            sve.setGivenKeywords(getGivenKeywords());
            sve.setWhenKeywords(getWhenKeywords());
            sve.setThenKeywords(getThenKeywords());

            return sve;
        }
    }
}
