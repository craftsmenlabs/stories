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
import java.util.stream.Collectors;

public class PluginExecutor {

    private final Logger logger = LoggerFactory.getLogger(PluginExecutor.class);
    private ConsoleReporter validationConsoleReporter = new ConsoleReporter();

    String STATUS = "To Do";

    public Rating execute(ApplicationConfig cfg, ScorerConfigCopy validationConfig) {
        Importer importer = getImporter(cfg);
        String data = importer.getDataAsString();
        Parser parser = getParser(cfg.getDataformat());

        List<Issue> issues =
                parser.getIssues(data)
                        .stream()
                        .filter(issue -> issue.getUserstory() != null )
                        .filter(issue -> !issue.getUserstory().isEmpty())
                        .collect(Collectors.toList());

        Backlog backlog = new Backlog();
        backlog.setIssues(issues);

        //TODO validateconfig causes a cyclic dependency here!!
        BacklogValidatorEntry backlogValidatorEntry = BacklogScorer.performScorer(backlog, new CurvedRanking(), validationConfig);


        //console report
        validationConsoleReporter.report(backlogValidatorEntry);

        //write file report
        if(cfg.getOutputfile() != null && !cfg.getOutputfile().isEmpty()) {
            new JsonFileReporter(new File(cfg.getOutputfile()))
                    .report(backlogValidatorEntry);
        }

        //Multiply by 100%
        return backlogValidatorEntry.getRating();
    }

    public Importer getImporter(ApplicationConfig cfg){
        if(restApiParametersAreSet(cfg)){
            logger.info("rest Api parameters are set, using JiraAPIImporter");
            return new JiraAPIImporter(cfg.getUrl(), cfg.getProjectkey(), cfg.getAuthkey(), STATUS);
        }else{
            logger.info("No rest Api parameters are set, using FileImporter on file: " + cfg.getInputfile());
            return new FileImporter(cfg.getInputfile());
        }
    }


    private boolean restApiParametersAreSet(ApplicationConfig cfg) {
        return cfg.getUrl() != null &&
                !cfg.getUrl().isEmpty() &&
                cfg.getAuthkey() != null &&
                !cfg.getAuthkey().isEmpty();
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
