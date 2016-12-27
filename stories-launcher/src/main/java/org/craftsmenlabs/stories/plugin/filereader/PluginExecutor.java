package org.craftsmenlabs.stories.plugin.filereader;

import lombok.RequiredArgsConstructor;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.config.ReportConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.config.StorynatorConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.community.CommunityDashboardReporter;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardConfigRetriever;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardReporter;
import org.craftsmenlabs.stories.importer.GithubAPIImporter;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.importer.TrelloAPIImporter;
import org.craftsmenlabs.stories.plugin.filereader.config.EnterpriseConfig;
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
    private final EnterpriseConfig enterpriseConfig;


    @Autowired
    private StorynatorConfig storynatorConfig;

    public Rating startApplication() {
        logger.info("Initializing Gareth Storynator");

        if (enterpriseConfig.isEnabled()) {
            // We should retrieve settings there.
            this.loadSettingsFromEnterpriseDashboard();
        }
        // Import the data
        Importer importer = getImporter(storynatorConfig.getSource().getType());
        Backlog backlog = importer.getBacklog();

        // Perform the backlog validation
        BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), storynatorConfig.getValidation());

        if ((backlogValidatorEntry.getBacklog().getBugs() == null || backlogValidatorEntry.getBacklog().getBugs().size() == 0)
                && (backlogValidatorEntry.getBacklog().getFeatures() == null || backlogValidatorEntry.getBacklog().getFeatures().size() == 0)) {
            throw new StoriesException("Sorry. No items to be found in de backlog for Storynator to process. Exiting Storynator.");
        }

        // Dashboard report?
        StoriesRun storiesRun = StoriesRun.builder()
                .summary(new SummaryBuilder().build(backlogValidatorEntry))
                .backlogValidatorEntry(backlogValidatorEntry)
                .runConfig(storynatorConfig.getValidation())
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
    private Importer getImporter(String enabled) {
        switch (enabled) {
            case "jira":
                SourceConfig.JiraConfig jiraConfig = storynatorConfig.getSource().getJira();
                logger.info("Using JiraAPIImporter for import." + jiraConfig.getUrl());
                return new JiraAPIImporter(storynatorConfig);
            case "trello":
                logger.info("Using TrelloAPIImporter for import.");
                SourceConfig.TrelloConfig trelloConfig = storynatorConfig.getSource().getTrello();
                return new TrelloAPIImporter(trelloConfig.getUrl(), trelloConfig.getProjectKey(), trelloConfig.getAuthKey(), trelloConfig.getToken());
            case "github":
                logger.info("Using GithubAPIImporter for import.");
                SourceConfig.GithubConfig githubConfig = storynatorConfig.getSource().getGithub();
                return new GithubAPIImporter(githubConfig.getUrl(), githubConfig.getProjectKey(), githubConfig.getAuthKey(), githubConfig.getToken());
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
        reporters.add(new ConsoleReporter(storynatorConfig.getValidation()));
        reporters.add(new SummaryConsoleReporter());

        ReportConfig reportConfig = storynatorConfig.getReport();
        if (reportConfig != null && reportConfig.getFile() != null && reportConfig.getFile().isEnabled()) {
            reporters.add(new JsonFileReporter(new File(reportConfig.getFile().getLocation())));
        }

        if (reportConfig != null && reportConfig.getDashboard() != null && reportConfig.getDashboard().isEnabled()) {
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
        try {
            EnterpriseDashboardConfigRetriever dashboardConfigRetriever = new EnterpriseDashboardConfigRetriever();
            this.storynatorConfig = dashboardConfigRetriever.retrieveSettings(enterpriseConfig.getUrl(), enterpriseConfig.getToken(), enterpriseConfig.getPassword());

        } catch (Exception e) {
            throw new StoriesException("Could not retrieve settings for the project: " + e.getMessage());
        }
    }
}
