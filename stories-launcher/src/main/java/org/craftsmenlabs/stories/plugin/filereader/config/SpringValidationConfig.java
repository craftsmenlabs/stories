package org.craftsmenlabs.stories.plugin.filereader.config;

import lombok.Data;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.validation.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "validation")
public class SpringValidationConfig {
    private BacklogValidatorConfig backlog;
    private FeatureValidatorConfig feature;
    private StoryValidatorConfig story;
    private CriteriaValidatorConfig criteria;
    private EstimationValidatorConfig estimation;
    private BugValidatorConfig bug;
    private EpicValidatorConfig epic;
    private TeamTaskValidatorConfig teamTask;

    public ValidationConfig convert() {
        ValidationConfig validationConfig = new ValidationConfig();

        validationConfig.setBacklog(this.getBacklog().convert());
        validationConfig.setFeature(this.getFeature().convert());
        validationConfig.setStory(this.getStory().convert());
        validationConfig.setCriteria(this.getCriteria().convert());
        validationConfig.setEstimation(this.getEstimation().convert());
        validationConfig.setBug(this.getBug().convert());
        validationConfig.setEpic(this.getEpic().convert());
        validationConfig.setTeamTask(this.getTeamTask().convert());

        return validationConfig;
    }
}
