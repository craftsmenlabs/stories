package org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig;

import lombok.Data;

@Data
public class ScorerConfigCopy {
    private ValidatorEntryCopy backlog;
    private ValidatorEntryCopy issue;
    private ValidatorEntryCopy story;
    private ValidatorEntryCopy criteria;
    private ValidatorEntryCopy estimation;


    @Data
    public static class ValidatorEntryCopy {
        private float ratingtreshold;


    }
}
