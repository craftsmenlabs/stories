package org.craftsmenlabs.stories.plugin.filereader;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
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
    private ValidatorEntry criteria;
    private ValidatorEntry estimation;

    public ScorerConfigCopy clone() {
        ScorerConfigCopy copy = new ScorerConfigCopy();

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

        public ScorerConfigCopy.ValidatorEntryCopy clone(){
            ScorerConfigCopy.ValidatorEntryCopy validatorEntryCopy = new ScorerConfigCopy.ValidatorEntryCopy();

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

        public ScorerConfigCopy.StoryValidatorEntryCopy clone() {
            ScorerConfigCopy.StoryValidatorEntryCopy sve = new ScorerConfigCopy.StoryValidatorEntryCopy();
            sve.setRatingtreshold(getRatingtreshold());
            sve.setActive(isActive());

            sve.setAsKeywords(getAsKeywords());
            sve.setIKeywords(getIKeywords());
            sve.setSoKeywords(getSoKeywords());

            return sve;
        }
    }
}
