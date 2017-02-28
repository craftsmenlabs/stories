package org.craftsmenlabs.stories.api.models.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;

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
                                .feature(new ValidationConfig.ValidatorEntry(0.6, true))
                                .bug(new ValidationConfig.BugValidatorEntry())
                                .criteria(new ValidationConfig.CriteriaValidatorEntry())
                                .estimation(new ValidationConfig.ValidatorEntry(0.6, true))
                                .epic(new ValidationConfig.EpicValidatorEntry())
                                .story(new ValidationConfig.StoryValidatorEntry())
                                .teamTask(new ValidationConfig.ValidatorEntry(0.6, true))
                                .build()

                )
                .source(
                        SourceConfig.builder()
                                .type("jira")
                                .trello(SourceConfig.TrelloConfig.builder().build())
                                .jira(SourceConfig.JiraConfig.builder().build())
                                .github(SourceConfig.GithubConfig.builder().build())
                                .build()
                )
                .filter(FilterConfig.builder().status("To Do").build())
                .build();

        //quick fix because of the absence of builders/allargsconfig
        config.getValidation().getBug().setActive(true);
        config.getValidation().getBug().setRatingThreshold(0.6);
        config.getValidation().getBug().setEnabledFields(Arrays.asList("priority", "reproduction_path", "environment", "expected_behaviour", "acceptation_criteria"));
        config.getValidation().getEpic().setActive(true);
        config.getValidation().getEpic().setRatingThreshold(0.6);
        config.getValidation().getEpic().setEnabledFields(Collections.singletonList("goal"));
        config.getValidation().getCriteria().setActive(true);
        config.getValidation().getCriteria().setRatingThreshold(0.6);
        config.getValidation().getCriteria().setGivenKeywords(Collections.singletonList("given"));
        config.getValidation().getCriteria().setWhenKeywords(Collections.singletonList("when"));
        config.getValidation().getCriteria().setThenKeywords(Collections.singletonList("then"));
        config.getValidation().getStory().setActive(true);
        config.getValidation().getStory().setRatingThreshold(0.6);
        config.getValidation().getStory().setAsKeywords(Collections.singletonList("As a"));
        config.getValidation().getStory().setIKeywords(Collections.singletonList("I want"));
        config.getValidation().getStory().setSoKeywords(Collections.singletonList("So that"));

        return config;
    }
}
