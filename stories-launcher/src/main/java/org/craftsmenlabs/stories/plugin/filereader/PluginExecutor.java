package org.craftsmenlabs.stories.plugin.filereader;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.craftsmenlabs.stories.api.models.*;
import org.craftsmenlabs.stories.api.models.config.*;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.community.CommunityDashboardReporter;
import org.craftsmenlabs.stories.connectivity.service.enterprise.EnterpriseDashboardReporter;
import org.craftsmenlabs.stories.importer.*;
import org.craftsmenlabs.stories.plugin.filereader.config.*;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.reporter.*;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PluginExecutor {
	private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
	public ValidationConfig validationConfig;
	public FieldMappingConfig fieldMappingConfig;
	public FilterConfig filterConfig;
	public ReportConfig reportConfig;

	@Autowired
	private Environment env;
	@Autowired
	private SpringReportConfig springReportConfig;
	@Autowired
	private SpringSourceConfig springSourceConfig;
	@Autowired
	private SpringFilterConfig springFilterConfig;
	@Autowired
	private SpringValidationConfig springValidationConfig;
	@Autowired
	private SpringFieldMappingConfig springFieldMappingConfig;

	public Rating startApplication() {
		logger.info("Starting stories plugin.");


		// Validate configs
		this.springReportConfig.validate();
		this.springSourceConfig.validate();
		this.springFieldMappingConfig.validate();

		// Convert configs
		validationConfig = springValidationConfig.convert();
		fieldMappingConfig = springFieldMappingConfig.convert();
		filterConfig = springFilterConfig.convert();
		reportConfig = springReportConfig.convert();

		// Import the data
		Importer importer = getImporter(springSourceConfig.getEnabled());
		Backlog backlog = importer.getBacklog();

		// Perform the backlog validation
		BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfig);


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

		//Multiply by 100%
		return backlogValidatorEntry.getRating();
	}

	/**
	 * Finds and initializes the right importer for the source enabled setting.
	 *
	 * @param enabled source
	 * @return Importer importer
	 */
	public Importer getImporter(String enabled) {
		switch(enabled) {
			case "jira":
				SpringSourceConfig.JiraConfig jiraConfig = springSourceConfig.getJira();
				logger.info("Using JiraAPIImporter for import." + jiraConfig.getUrl());
				return new JiraAPIImporter(jiraConfig.getUrl(),jiraConfig.getProjectKey(), jiraConfig.getAuthKey(), fieldMappingConfig, filterConfig);
			case "trello":
				logger.info("Using TrelloAPIImporter for import.");
				SpringSourceConfig.TrelloConfig trelloConfig = springSourceConfig.getTrello();
				return new TrelloAPIImporter(trelloConfig.getUrl(), trelloConfig.getProjectKey(), trelloConfig.getAuthKey(), trelloConfig.getToken());
			default:
				throw new StoriesException(StoriesException.ERR_SOURCE_ENABLED_MISSING);
		}
	}

	/**
	 * Finds out which reporters should be used for this run
	 * @return List of reporters
	 */
	private List<Reporter> getReporters() {
		List<Reporter> reporters = new LinkedList<>();
		reporters.add(new ConsoleReporter(this.validationConfig));
		reporters.add(new SummaryConsoleReporter());

		if(this.springReportConfig.getFile() != null && this.springReportConfig.getFile().isEnabled()) {
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
}
