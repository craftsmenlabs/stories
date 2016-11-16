package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.importer.TrelloAPIImporter;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.Parser;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.plugin.filereader.config.SpringFieldMappingConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.ApplicationConfig;
import org.craftsmenlabs.stories.plugin.filereader.config.SpringValidationConfig;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.reporter.ConsoleReporter;
import org.craftsmenlabs.stories.reporter.JsonFileReporter;
import org.craftsmenlabs.stories.reporter.SummaryConsoleReporter;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PluginExecutor {

	private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
	public ValidationConfig springValidationConfigCopy;
	public FieldMappingConfig fieldMappingConfigCopy;

	private ConsoleReporter validationConsoleReporter = new ConsoleReporter();
	@Autowired
	private ConnectivityService dashboardConnectivity;
	@Autowired
	private ApplicationConfig applicationConfig;
	@Autowired
	private SpringValidationConfig springValidationConfig;
	@Autowired
	private SpringFieldMappingConfig springFieldMappingConfig;

	public Rating startApplication() {
		logger.info("Starting stories plugin.");

		springValidationConfigCopy = springValidationConfig.convert();
		fieldMappingConfigCopy = springFieldMappingConfig.convert();

		Importer importer = getImporter(applicationConfig);
		String data = importer.getDataAsString();
		Parser parser = getParser(applicationConfig.getDataformat());

		List<Issue> issues = parser.getIssues(data).stream()
				.filter(issue -> issue.getUserstory() != null)
				.filter(issue -> !issue.getUserstory().isEmpty())
				.collect(Collectors.toList());

		Backlog backlog = new Backlog();
		backlog.setIssues(issues);

		BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), springValidationConfigCopy);

		//console report
		validationConsoleReporter.report(backlogValidatorEntry, springValidationConfigCopy);

        StoriesRun storiesRun = StoriesRun.builder()
                .summary(new SummaryBuilder().build(backlogValidatorEntry))
                .backlogValidatorEntry(backlogValidatorEntry)
				.runConfig(springValidationConfigCopy)
				.runDateTime(LocalDateTime.now())
				.build();

        dashboardConnectivity.sendData(storiesRun);

        //write file report
		if (isOutputFileSet(applicationConfig)) {
			new JsonFileReporter(new File(applicationConfig.getOutputfile()))
					.report(backlogValidatorEntry);
		}

		new SummaryConsoleReporter().reportJson(backlogValidatorEntry);

		//Multiply by 100%
		return backlogValidatorEntry.getRating();
	}

	private boolean isOutputFileSet(ApplicationConfig applicationConfig) {
		return applicationConfig.getOutputfile() != null && !applicationConfig.getOutputfile().isEmpty();
	}

	public Importer getImporter(ApplicationConfig applicationConfig) {
		if (tokenIsSet(applicationConfig)) {
			logger.info("projectToken is set, using TrelloAPIImporter");
			return new TrelloAPIImporter(applicationConfig.getUrl(), applicationConfig.getProjectkey(), applicationConfig.getAuthkey(), applicationConfig.getToken());
		} else if (restApiParametersAreSet(applicationConfig)) {
			logger.info("rest Api parameters are set, using JiraAPIImporter");
			return new JiraAPIImporter(applicationConfig.getUrl(), applicationConfig.getProjectkey(), applicationConfig.getAuthkey(), applicationConfig.getStatus());
		} else {
			logger.error(getRunParameters());
			throw new IllegalArgumentException(getRunParameters());
		}
	}

	private boolean tokenIsSet(ApplicationConfig applicationConfig) {
		return applicationConfig.getToken() != null &&
				!applicationConfig.getToken().isEmpty();
	}

	private boolean restApiParametersAreSet(ApplicationConfig applicationConfig) {
		return applicationConfig.getUrl() != null &&
				!applicationConfig.getUrl().isEmpty() &&
				applicationConfig.getAuthkey() != null &&
				!applicationConfig.getAuthkey().isEmpty();
	}

	/**
	 * Set the parser based on the data format:
	 * jirajson, trellojson, etc.
	 *
	 * @param dataFormat the format of the data
	 */
	private Parser getParser(String dataFormat) {
		Parser parser;
		switch (dataFormat) {
			case ("jirajson"):
				parser = new JiraJsonParser(fieldMappingConfigCopy, applicationConfig.getStatus());
				break;
			case ("trellojson"):
				parser = new TrelloJsonParser();
				break;
			default:
				logger.warn("No dataformat specified, please use the parameter -df to enter a dataformat, " +
						"such as {jirajson, trellojson}. By default I will now use jirajson.");
				parser = new JiraJsonParser(fieldMappingConfigCopy, applicationConfig.getStatus());
				break;
		}
		return parser;
	}

	private String getRunParameters() {
		return "No api parameters or file is set. For the api please use:\n\n" +
				"-- application.url = <http://jira.demo.com host without the jira api extension>\n" +
				"-- application.authkey = <base64 encoded username:password for Jira>\n" +
				"-- application.projectkey = <projectkey used in Jira>\n" +
				"-- application.status = <status for backlogitems used in Jira>\n\n\n" +
				"or to use a file use:\n" +
				"-- application.inputfile = <PATH+FILENAME TO JSON FILE>";
	}
}
