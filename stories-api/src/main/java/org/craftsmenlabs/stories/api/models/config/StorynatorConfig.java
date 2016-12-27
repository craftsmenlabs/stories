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

    //used in the gareth dashboard
    @SuppressWarnings("unused")
    public static StorynatorConfig createDefault() {
        return StorynatorConfig.builder()
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
                                .backlog(new ValidationConfig.ValidatorEntry())
                                .feature(new ValidationConfig.ValidatorEntry())
                                .bug(new ValidationConfig.BugValidatorEntry())
                                .criteria(new ValidationConfig.CriteriaValidatorEntry())
                                .estimation(new ValidationConfig.ValidatorEntry())
                                .epic(new ValidationConfig.EpicValidatorEntry())
                                .teamTask(new ValidationConfig.ValidatorEntry())
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
    }
}
