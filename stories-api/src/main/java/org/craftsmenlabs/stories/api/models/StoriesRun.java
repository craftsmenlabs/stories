package org.craftsmenlabs.stories.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.summary.Summary;

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
    private ValidationConfig runConfig;

    //the complete validated backlog
    @JsonProperty("backlogValidatorEntry")
    private BacklogValidatorEntry backlogValidatorEntry;

    //Statistics summary
    @JsonProperty("summary")
    private Summary summary;
}
