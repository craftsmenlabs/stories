package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.importer.FileImporter;
import org.craftsmenlabs.stories.importer.Importer;
import org.craftsmenlabs.stories.importer.JiraAPIImporter;
import org.craftsmenlabs.stories.isolator.parser.JiraCSVParser;
import org.craftsmenlabs.stories.isolator.parser.JiraJsonParser;
import org.craftsmenlabs.stories.isolator.parser.Parser;
import org.craftsmenlabs.stories.reporter.ConsoleReporter;
import org.craftsmenlabs.stories.reporter.JsonFileReporter;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.craftsmenlabs.stories.scoring.BacklogScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PluginExecutor {

    private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
    private ConsoleReporter validationConsoleReporter = new ConsoleReporter();

    public Rating execute(ApplicationConfig cfg, ScorerConfigCopy validationConfig) {
        Parser parser = getParser(cfg.getDataformat());

        BacklogValidatorEntry backlogValidatorEntry =
                getImporter(cfg)
                    .flatMap(importer -> importer.getDataAsString())
                    .map(s -> parser.getIssues(s).stream()
                            .filter(issue -> issue.getUserstory() != null )
                            .filter(issue -> !issue.getUserstory().isEmpty())
                            .collect(Collectors.toList()))
                    .map(issues -> {
                        Backlog backlog = new Backlog();
                        backlog.setIssues(issues);
                        return backlog;
                    })
                    .map(backlog -> BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfig))
                        .orElse(BacklogValidatorEntry.builder().build());

        //console report
        validationConsoleReporter.report(backlogValidatorEntry);

        //write file report
        if(isOutputFileSet(cfg)) {
            new JsonFileReporter(new File(cfg.getOutputfile()))
                    .report(backlogValidatorEntry);
        }

        //Multiply by 100%
        return backlogValidatorEntry.getRating();
    }

    private boolean isOutputFileSet(ApplicationConfig cfg) {
        return cfg.getOutputfile() != null && !cfg.getOutputfile().isEmpty();
    }

    public Optional<Importer> getImporter(ApplicationConfig cfg){
        if(restApiParametersAreSet(cfg)){
            logger.info("rest Api parameters are set, using JiraAPIImporter");
            return Optional.of(new JiraAPIImporter(cfg.getUrl(), cfg.getProjectkey(), cfg.getAuthkey(), cfg.getStatus()));
        }else if(fileParametersAreSet(cfg)){
            logger.info("input file is set: " + cfg.getInputfile());
            return Optional.of(new FileImporter(cfg.getInputfile()));
        }else{
            logger.error("No api parameters or file is set. For the api please use:\n\n" +
                    "-- application.url = <http://jira.demo.com host without the jira api extension>\n" +
                    "-- application.authkey = <base64 encoded username:password for Jira>\n" +
                    "-- application.projectkey = <projectkey used in Jira>\n" +
                    "-- application.status = <status for backlogitems used in Jira>\n\n\n" +
                    "or to use a file use:\n" +
                    "-- application.inputfile = <PATH+FILENAME TO JSON FILE>");
            return Optional.empty();
        }
    }


    private boolean restApiParametersAreSet(ApplicationConfig cfg) {
        return cfg.getUrl() != null &&
                !cfg.getUrl().isEmpty() &&
                cfg.getAuthkey() != null &&
                !cfg.getAuthkey().isEmpty();
    }

    private boolean fileParametersAreSet(ApplicationConfig cfg){
        return cfg.getInputfile()!= null &&
                !cfg.getInputfile().isEmpty();
    }


    /**
     * Set the parser based on the data format:
     * jirajson, jiracsv, trellojson, etc.
     * @param dataFormat the format of the data
     */
    private Parser getParser(String dataFormat) {
        Parser parser;
        switch( dataFormat ){
            case("jiracsv"):
                parser = new JiraCSVParser();
                break;
            case("jirajson"):
                parser = new JiraJsonParser();
                break;
            default:
                logger.warn("No dataformat specified, please use the parameter -df to enter a dataformat, " +
                        "such as {jirajson, jiracsv}. By default I will now use jirajson.");
                parser = new JiraJsonParser();
                break;
        }
        return parser;
    }
}
