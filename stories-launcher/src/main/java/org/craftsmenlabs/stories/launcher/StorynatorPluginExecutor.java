package org.craftsmenlabs.stories.launcher;

import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesReport;
import org.craftsmenlabs.stories.api.models.config.ReportConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.connectivity.service.community.CommunityDashboardReporter;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardReporter;
import org.craftsmenlabs.stories.importer.GithubAPIImporter;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.importer.TrelloAPIImporter;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.reporter.ConsoleReporter;
import org.craftsmenlabs.stories.reporter.JsonFileReporter;
import org.craftsmenlabs.stories.reporter.SummaryConsoleReporter;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class StorynatorPluginExecutor {
    private StorynatorConfig storynatorConfig;
    private StorynatorVersion storynatorVersion;
    private StorynatorLogger logger;

    public ValidatedBacklog runApplication(StorynatorConfig config, StorynatorVersion version, StorynatorLogger logger) {
        this.logger = logger;
        this.storynatorConfig = config;
        this.storynatorVersion = version;
        this.logger.info("Initializing Gareth Storynator");

        // Import the data
        Importer importer = getImporter(storynatorConfig.getSource().getType());
        Backlog backlog = importer.getBacklog();

        // Perform the backlog validation
        BacklogScorer scorer = new BacklogScorer(storynatorConfig.getValidation(), new CurvedRanking());
        ValidatedBacklog validatedBacklog = scorer.validate(backlog);

        if ((validatedBacklog.getItem().getIssues() == null || validatedBacklog.getItem().getIssues().isEmpty())) {
            throw new StoriesException("Sorry. No issues to be found in de backlog for Storynator to process. Exiting Storynator.");
        }

        // Dashboard report?
        StoriesReport storiesReport = StoriesReport.builder()
                .summary(new SummaryBuilder().build(validatedBacklog))
                .validatedBacklog(validatedBacklog)
                .runConfig(storynatorConfig.getValidation())
                .runDateTime(LocalDateTime.now())
                .build();


        for (Reporter reporter : this.getReporters()) {
            reporter.report(storiesReport);
        }

        return validatedBacklog;
    }

    /**
     * Finds and initializes the right importer for the source type setting.
     *
     * @param enabled source
     * @return Importer importer
     */
    private Importer getImporter(String enabled) {
        switch (enabled) {
            case "jira":
                SourceConfig.JiraConfig jiraConfig = storynatorConfig.getSource().getJira();
                logger.info("Using JiraAPIImporter for import." + jiraConfig.getUrl());
                return new JiraAPIImporter(logger, storynatorConfig);
            case "trello":
                logger.info("Using TrelloAPIImporter for import.");
                SourceConfig.TrelloConfig trelloConfig = storynatorConfig.getSource().getTrello();
                return new TrelloAPIImporter(logger, trelloConfig.getUrl(), trelloConfig.getProjectKey(), trelloConfig.getAuthKey(), trelloConfig.getToken());
            case "github":
                logger.info("Using GithubAPIImporter for import.");
                SourceConfig.GithubConfig githubConfig = storynatorConfig.getSource().getGithub();
                return new GithubAPIImporter(logger, githubConfig.getUrl(), githubConfig.getProject(), githubConfig.getOwner(), githubConfig.getToken());
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
        reporters.add(new ConsoleReporter(logger, storynatorConfig.getValidation()));
        reporters.add(new SummaryConsoleReporter(logger));

        ReportConfig reportConfig = storynatorConfig.getReport();
        if (reportConfig != null && reportConfig.getFile() != null && reportConfig.getFile().isEnabled()) {
            reporters.add(new JsonFileReporter(new File(reportConfig.getFile().getLocation())));
        }

        if (reportConfig != null && reportConfig.getDashboard() != null && reportConfig.getDashboard().isEnabled()) {
            if (this.storynatorVersion == StorynatorVersion.ENTERPRISE) {
                logger.debug("Started enterprise version of reporter.");
                reporters.add(new EnterpriseDashboardReporter(logger, reportConfig));
            } else {
                logger.debug("Started community version of reporter.");
                reporters.add(new CommunityDashboardReporter(logger));
            }
        }

        return reporters;
    }
}
