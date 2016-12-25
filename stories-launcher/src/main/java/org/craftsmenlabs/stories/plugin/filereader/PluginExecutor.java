package org.craftsmenlabs.stories.plugin.filereader;

import lombok.RequiredArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.config.*;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.community.CommunityDashboardReporter;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardConfigRetriever;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardReporter;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.importer.TrelloAPIImporter;
import org.craftsmenlabs.stories.plugin.filereader.config.*;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.reporter.ConsoleReporter;
import org.craftsmenlabs.stories.reporter.JsonFileReporter;
import org.craftsmenlabs.stories.reporter.SummaryConsoleReporter;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PluginExecutor {
    private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
    private final Environment env;
    private final SpringReportConfig springReportConfig;
    private final SpringSourceConfig springSourceConfig;
    private final SpringFilterConfig springFilterConfig;
    private final SpringValidationConfig springValidationConfig;
    private final SpringFieldMappingConfig springFieldMappingConfig;
    private ValidationConfig validationConfig;
    private FieldMappingConfig fieldMappingConfig;
    private FilterConfig filterConfig;
    private ReportConfig reportConfig;
    private SourceConfig sourceConfig;

    public Rating startApplication() {
        logger.info("Initializing Gareth Storynator");


        if (this.springReportConfig.getDashboard().isEnabled()) {
            // We should retrieve settings there.
            this.loadSettingsFromEnterpriseDashboard();
        } else {
            // Validate configs
            this.springReportConfig.validate();
            this.springSourceConfig.validate();
            this.springFieldMappingConfig.validate();

            // Convert configs
            validationConfig = springValidationConfig.convert();
            fieldMappingConfig = springFieldMappingConfig.convert();
            filterConfig = springFilterConfig.convert();
            reportConfig = springReportConfig.convert();
            sourceConfig = springSourceConfig.convert();
        }

        // Import the data
        Importer importer = getImporter(sourceConfig.getType());
        Backlog backlog = importer.getBacklog();

        // Perform the backlog validation
        BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfig);

        if ((backlogValidatorEntry.getBacklog().getBugs() == null || backlogValidatorEntry.getBacklog().getBugs().size() == 0)
                && (backlogValidatorEntry.getBacklog().getFeatures() == null || backlogValidatorEntry.getBacklog().getFeatures().size() == 0)) {
            throw new StoriesException("Sorry. No items to be found in de backlog for Storynator to process. Exiting Storynator.");
        }

        // Dashboard report?
        StoriesRun storiesRun = StoriesRun.builder()
                .summary(new SummaryBuilder().build(backlogValidatorEntry))
                .backlogValidatorEntry(backlogValidatorEntry)
                .runConfig(validationConfig)
                .runDateTime(LocalDateTime.now())
                .build();


        for (Reporter reporter : this.getReporters()) {
            reporter.report(storiesRun);
        }

        return backlogValidatorEntry.getRating();
    }

    /**
     * Finds and initializes the right importer for the source type setting.
     *
     * @param enabled source
     * @return Importer importer
     */
    public Importer getImporter(String enabled) {
        switch (enabled) {
            case "jira":
                SourceConfig.JiraConfig jiraConfig = sourceConfig.getJira();
                logger.info("Using JiraAPIImporter for import." + jiraConfig.getUrl());
                return new JiraAPIImporter(jiraConfig.getUrl(), jiraConfig.getProjectKey(), jiraConfig.getAuthKey(), fieldMappingConfig, filterConfig);
            case "trello":
                logger.info("Using TrelloAPIImporter for import.");
                SourceConfig.TrelloConfig trelloConfig = sourceConfig.getTrello();
                return new TrelloAPIImporter(trelloConfig.getUrl(), trelloConfig.getProjectKey(), trelloConfig.getAuthKey(), trelloConfig.getToken());
            default:
                throw new StoriesException(StoriesException.ERR_SOURCE_ENABLED_MISSING);
        }
    }

    /**
     * Finds out which reporters should be used for this run
     *
     * @return List of reporters
     */
    private List<Reporter> getReporters() {
        List<Reporter> reporters = new LinkedList<>();
        reporters.add(new ConsoleReporter(this.validationConfig));
        reporters.add(new SummaryConsoleReporter());

        if (this.springReportConfig.getFile() != null && this.springReportConfig.getFile().isEnabled()) {
            reporters.add(new JsonFileReporter(new File(this.springReportConfig.getFile().getLocation())));
        }

        if (this.springReportConfig.getDashboard() != null && this.springReportConfig.getDashboard().isEnabled()) {
            List<String> profiles = Arrays.asList(env.getActiveProfiles());
            if (profiles.contains("enterprise") && !profiles.contains("community")) {
                logger.debug("Started enterprise version of reporter.");
                reporters.add(new EnterpriseDashboardReporter(reportConfig));
            } else {
                logger.debug("Started community version of reporter.");
                reporters.add(new CommunityDashboardReporter());
            }
        }

        return reporters;
    }

    /**
     *
     */
    private void loadSettingsFromEnterpriseDashboard() {
        EnterpriseDashboardConfigRetriever.ConfigMapping mapping =
                EnterpriseDashboardConfigRetriever.retrieveSettings(this.springReportConfig.getDashboard().getUrl(), this.springReportConfig.getDashboard().getToken());

        this.fieldMappingConfig = mapping.getFieldMappingConfig();
        this.filterConfig = mapping.getFilterConfig();
        this.validationConfig = mapping.getValidationConfig();
    }
}
