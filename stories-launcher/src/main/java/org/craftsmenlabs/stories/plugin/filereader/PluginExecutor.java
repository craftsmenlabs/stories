package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.craftsmenlabs.stories.importer.FileImporter;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.importer.TrelloAPIImporter;
import org.craftsmenlabs.stories.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.Parser;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.reporter.ConsoleReporter;
import org.craftsmenlabs.stories.reporter.SummaryConsoleReporter;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PluginExecutor {

	private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
	private ConsoleReporter validationConsoleReporter = new ConsoleReporter();

	@Autowired
	private ConnectivityService dashboardConnectivity;

	public Rating execute(ApplicationConfig cfg, ValidationConfigCopy validationConfig)
	{
		Importer importer = getImporter(cfg);
		String data = importer.getDataAsString();
		Parser parser = getParser(cfg.getDataformat());

		List<Issue> issues = parser.getIssues(data).stream()
			.filter(issue -> issue.getUserstory() != null)
			.filter(issue -> !issue.getUserstory().isEmpty())
			.collect(Collectors.toList());

		Backlog backlog = new Backlog();
		backlog.setIssues(issues);

		BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfig);

		//console report
		validationConsoleReporter.report(backlogValidatorEntry, validationConfig);

        StoriesRun storiesRun = StoriesRun.builder()
                .summary(new SummaryBuilder().build(backlogValidatorEntry))
                .backlogValidatorEntry(backlogValidatorEntry)
                .runConfig(validationConfig)
                .runDateTime(LocalDateTime.now())
                .build();

        dashboardConnectivity.sendData(storiesRun);

        //write file report
//        if(isOutputFileSet(cfg)) {
//            new JsonFileReporter(new File(cfg.getOutputfile()))
//                    .report(backlogValidatorEntry);
//        }

		new SummaryConsoleReporter().reportJson(backlogValidatorEntry);

		//Multiply by 100%
		return backlogValidatorEntry.getRating();
	}

	private boolean isOutputFileSet(ApplicationConfig cfg)
	{
		return cfg.getOutputfile() != null && !cfg.getOutputfile().isEmpty();
	}

	public Importer getImporter(ApplicationConfig cfg)
	{
		if (tokenIsSet(cfg))
		{
            logger.info("projectToken is set, using TrelloAPIImporter");
            return new TrelloAPIImporter(cfg.getUrl(), cfg.getProjectkey(), cfg.getAuthkey(), cfg.getToken());
		}
		else if (restApiParametersAreSet(cfg))
		{
			logger.info("rest Api parameters are set, using JiraAPIImporter");
			return new JiraAPIImporter(cfg.getUrl(), cfg.getProjectkey(), cfg.getAuthkey(), cfg.getStatus());
		}
		else if (fileParametersAreSet(cfg))
		{
			logger.info("input file is set: " + cfg.getInputfile());
			return new FileImporter(cfg.getInputfile());
		}
		else
		{
			logger.error(getRunParameters());
			throw new IllegalArgumentException(getRunParameters());
		}
	}

	private boolean tokenIsSet(ApplicationConfig cfg)
	{
		return cfg.getToken() != null &&
			!cfg.getToken().isEmpty();
	}

	private boolean restApiParametersAreSet(ApplicationConfig cfg)
	{
		return cfg.getUrl() != null &&
			!cfg.getUrl().isEmpty() &&
			cfg.getAuthkey() != null &&
			!cfg.getAuthkey().isEmpty();
	}

	private boolean fileParametersAreSet(ApplicationConfig cfg)
	{
		return cfg.getInputfile() != null &&
			!cfg.getInputfile().isEmpty();
	}

	/**
	 * Set the parser based on the data format:
	 * jirajson, jiracsv, trellojson, etc.
	 *
	 * @param dataFormat the format of the data
	 */
	private Parser getParser(String dataFormat)
	{
		Parser parser;
		switch (dataFormat)
		{
		case ("jiracsv"):
			parser = new JiraCSVParser();
			break;
		case ("jirajson"):
			parser = new JiraJsonParser();
			break;
		case ("trellojson"):
			parser = new TrelloJsonParser();
			break;
		default:
			logger.warn("No dataformat specified, please use the parameter -df to enter a dataformat, " +
				"such as {jirajson, jiracsv}. By default I will now use jirajson.");
			parser = new JiraJsonParser();
			break;
		}
		return parser;
	}

	private String getRunParameters()
	{
		return "No api parameters or file is set. For the api please use:\n\n" +
			"-- application.url = <http://jira.demo.com host without the jira api extension>\n" +
			"-- application.authkey = <base64 encoded username:password for Jira>\n" +
			"-- application.projectkey = <projectkey used in Jira>\n" +
			"-- application.status = <status for backlogitems used in Jira>\n\n\n" +
			"or to use a file use:\n" +
			"-- application.inputfile = <PATH+FILENAME TO JSON FILE>";
	}
}
