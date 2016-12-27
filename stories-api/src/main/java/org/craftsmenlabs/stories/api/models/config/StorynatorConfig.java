package org.craftsmenlabs.stories.api.models.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorynatorConfig {
    private SourceConfig source;
    private ReportConfig report;
    private ValidationConfig validation;
    private FilterConfig filter;
    private FieldMappingConfig fieldMapping;

    public static StorynatorConfig createDefault() {
        StorynatorConfig config = StorynatorConfig.builder()
                .fieldMapping(
                        FieldMappingConfig.builder()
                                .rank("")
                                .feature(FieldMappingConfig.FeatureMapping.builder().build())
                                .bug(FieldMappingConfig.BugMapping.builder().build())
                                .epic(FieldMappingConfig.EpicMapping.builder().build())
                                .teamTask(FieldMappingConfig.TeamTaskMapping.builder().build())
                                .build()
                )
                .validation(
                        ValidationConfig.builder()
                                .backlog(new ValidationConfig.ValidatorEntry(60, true))
                                .feature(new ValidationConfig.ValidatorEntry(0.6f, true))
                                .bug(new ValidationConfig.BugValidatorEntry())
                                .criteria(new ValidationConfig.CriteriaValidatorEntry())
                                .estimation(new ValidationConfig.ValidatorEntry(0.6f, true))
                                .epic(new ValidationConfig.EpicValidatorEntry())
                                .teamTask(new ValidationConfig.ValidatorEntry(0.6f, true))
                                .build()

                )
                .source(
                        SourceConfig.builder()
                                .type("jira")
                                .trello(SourceConfig.TrelloConfig.builder().build())
                                .jira(SourceConfig.JiraConfig.builder().build())
                                .build()
                )
                .filter(FilterConfig.builder().status("To Do").build())
                .build();

        config.getValidation().getBug().setActive(true);
        config.getValidation().getBug().setRatingThreshold(0.6f);
        config.getValidation().getEpic().setActive(true);
        config.getValidation().getEpic().setRatingThreshold(0.6f);
        config.getValidation().getCriteria().setActive(true);
        config.getValidation().getCriteria().setRatingThreshold(0.6f);

        return config;
    }
}
