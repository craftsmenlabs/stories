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
    private ValidatorEntry feature;
    private StoryValidatorEntry story;
    private CriteriaValidatorEntry criteria;
    private ValidatorEntry estimation;
    private BugValidatorEntry bug;
    private EpicValidatorEntry epic;

    public ValidationConfig convert() {
        ValidationConfig validationConfig = new ValidationConfig();

        validationConfig.setBacklog(this.getBacklog().convert());
        validationConfig.setFeature(this.getFeature().convert());
        validationConfig.setStory(this.getStory().convert());
        validationConfig.setCriteria(this.getCriteria().convert());
        validationConfig.setEstimation(this.getEstimation().convert());
        validationConfig.setBug(this.getBug().convert());
        validationConfig.setEpic(this.getEpic().convert());

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

    @Data
    public static class BugValidatorEntry extends ValidatorEntry {
        private List<String> enabledFields;

        public ValidationConfig.BugValidatorEntry convert() {
            ValidationConfig.BugValidatorEntry bug = new ValidationConfig.BugValidatorEntry();
            bug.setActive(isActive());
            bug.setRatingtreshold(getRatingtreshold());
            bug.setEnabledFields(enabledFields);
            return bug;
        }
    }

    @Data
    public static class EpicValidatorEntry extends ValidatorEntry {
        private List<String> enabledFields;

        public ValidationConfig.EpicValidatorEntry convert() {
            ValidationConfig.EpicValidatorEntry epic = new ValidationConfig.EpicValidatorEntry();
            epic.setActive(isActive());
            epic.setRatingtreshold(getRatingtreshold());
            epic.setEnabledFields(getEnabledFields());

            return epic;
        }
    }
}
