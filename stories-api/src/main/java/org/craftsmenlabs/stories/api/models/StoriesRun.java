package org.craftsmenlabs.stories.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.summary.Summary;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;

import java.time.LocalDateTime;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoriesRun {
    //key to uniquely identify the customer
    private String customerApiKey;

    //key to uniquely identify the project
    private String projectApiKey;

    //short project name
    private String projectKey;

    //full project name
    private String projectName;

    //The time the run ended
    private LocalDateTime runDateTime;

    //Used to store the run config of this run instance
    private ValidationConfigCopy runConfig;

    //the complete validated backlog
    private BacklogValidatorEntry backlogValidatorEntry;

    //Statistics summary
    private Summary summary;
}
