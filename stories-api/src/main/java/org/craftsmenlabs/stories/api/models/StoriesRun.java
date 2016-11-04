package org.craftsmenlabs.stories.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("projectToken")
    private String projectToken;

    //The time the run ended
    @JsonProperty("runDateTime")
    private LocalDateTime runDateTime;

    //Used to store the run config of this run instance
    @JsonProperty("runConfig")
    private ValidationConfigCopy runConfig;

    //the complete validated backlog
    @JsonProperty("backlogValidatorEntry")
    private BacklogValidatorEntry backlogValidatorEntry;

    //Statistics summary
    @JsonProperty("summary")
    private Summary summary;
}
