package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="validation")
public class ValidationConfig {
    private ValidatorEntry backlog;
    private ValidatorEntry issue;
    private ValidatorEntry story;
    private ValidatorEntry criteria;
    private ValidatorEntry estimation;


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

    public ScorerConfigCopy clone(){
        ScorerConfigCopy copy = new ScorerConfigCopy();

        copy.setBacklog(this.getBacklog().clone());
        copy.setIssue(this.getIssue().clone());
        copy.setStory(this.getStory().clone());
        copy.setCriteria(this.getCriteria().clone());
        copy.setEstimation(this.getEstimation().clone());

        return copy;
    }
}
