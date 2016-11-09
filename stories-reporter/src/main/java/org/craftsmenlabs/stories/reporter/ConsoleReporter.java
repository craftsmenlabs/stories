package org.craftsmenlabs.stories.reporter;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.*;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ConsoleReporter
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final String[] COLORS = {
            ANSI_BLACK,
            ANSI_RED,
            ANSI_GREEN,
            ANSI_YELLOW,
            ANSI_BLUE,
            ANSI_PURPLE,
            ANSI_CYAN,
    };
    private static final int MAX_SCORE = 100;
    private final Logger logger = LoggerFactory.getLogger(ConsoleReporter.class);
    private String prefix = ANSI_PURPLE;
    private String postfix = ANSI_RESET;
    private String storynator =
            "   ,d88~/\\   d8                                                d8                   \n" +
                    "   8888/   _d88__  e88~-_  888-~\\ Y88b  / 888-~88e   /~~~8e  _d88__  e88~-_  888-~\\ \n" +
                    "   `Y88b    888   d888   i 888     Y888/  888  888       88b  888   d888   i 888    \n" +
                    "    `Y88b,  888   8888   | 888      Y8/   888  888  e88~-888  888   8888   | 888    \n" +
                    "     /8888  888   Y888   ' 888       Y    888  888 C888  888  888   Y888   ' 888    \n" +
                    "   \\/_88P'  \"88_/  \"88_-~  888      /     888  888  \"88_-888  \"88_/  \"88_-~  888    \n" +
                    "                                  _/";

    public void report(BacklogValidatorEntry backlogValidatorEntry, ValidationConfigCopy validationConfigCopy) {
        //header
        Random r = new Random();
        String stry = storynator.chars().mapToObj(chr ->"" + COLORS[r.nextInt(COLORS.length)] + (char)chr + ANSI_RESET).collect(Collectors.joining(""));

        log("\n\n\n" + stry + " \n\n\n");
        log("------------------------------------------------------------");
        log("--           Validator configuration                      --");
        log("------------------------------------------------------------");
        reportOnConfig(validationConfigCopy);

        log("------------------------------------------------------------");
        log("--               verbose output                           --");
        log("------------------------------------------------------------");

        //verbose output
        List<IssueValidatorEntry> entries = backlogValidatorEntry.getIssueValidatorEntries();
        entries.forEach(issue -> reportOnIssue(issue));

        //show backlog violations
        log("------------------------------------------------------------");
        backlogValidatorEntry.getViolations().forEach(violation -> log(violation.toString()));

        //Summary
        prefix = backlogValidatorEntry.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;
        log("\n\n\n" + stry + " \n\n\n");
        log("------------------------------------------------------------");
        log("--                  Storynator report                     --");
        log("------------------------------------------------------------");

        log("Processed a total of " + entries.size() + " issues.");
        log("Backlog score of "
            + new DecimalFormat("#.##").format(backlogValidatorEntry.getPointsValuation() * 100f)
            + " / "
            + MAX_SCORE);
        log("Rated: " + backlogValidatorEntry.getRating() + "  (with threshold on: " + validationConfigCopy.getBacklog()
            .getRatingtreshold() + ")");
    }

    public void reportOnIssue(IssueValidatorEntry issue){
        prefix = issue.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;

        log("------------------------------------------------------------");
        log("Issue "
                        + issue.getIssue().getKey()
                + " Item total ("
                + new DecimalFormat("#.#").format(issue.getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ") \t"
                + " US ("
                + new DecimalFormat("#.#").format(issue.getUserStoryValidatorEntry().getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ")\t"
                + " AC ("
                + new DecimalFormat("#.#").format(issue.getAcceptanceCriteriaValidatorEntry().getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ")\t"
                  );
        issue.getViolations()
                .forEach(violation ->
                        log("Violation found:" + violation.toString()));

        reportOnUserstory(issue.getUserStoryValidatorEntry());
        reportOnAcceptanceCriteria(issue.getAcceptanceCriteriaValidatorEntry());
        reportOnEstimation(issue.getEstimationValidatorEntry());
    }

    public void reportOnUserstory(UserStoryValidatorEntry entry){
        prefix = entry.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;


        String userstory = entry.getUserStory().replace("\n", " ").replace("\r", "");
        log("\t Userstory: (" + entry.getPointsValuation() + ") " + userstory);

        reportOnViolations(entry.getViolations());
    }

    public void reportOnAcceptanceCriteria(AcceptanceCriteriaValidatorEntry entry){
        prefix = entry.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;

        String criteria = entry.getAcceptanceCriteria().replace("\n", " ").replace("\r", "");
        log("\t Criteria: (" + entry.getPointsValuation() + ") " + criteria );

        reportOnViolations(entry.getViolations());
    }

    public void reportOnEstimation(EstimationValidatorEntry entry){
        prefix = entry.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;

        Float estimation = entry.getEstimation();
        log("\t Estimation: (" +entry.getPointsValuation() + ")" + estimation );

        reportOnViolations(entry.getViolations());
    }

    public void reportOnViolations(List<Violation> violations){
        violations.forEach(violation ->
                log("\t\t Violation found: " + violation.toString()));
    }

    private void reportOnConfig(ValidationConfigCopy validationConfigCopy) {
        log("backlog: " + validationConfigCopy.getBacklog().toString());
        log("issues: " + validationConfigCopy.getIssue());
        log("stories: " + validationConfigCopy.getStory());
        log("criteria: " + validationConfigCopy.getCriteria());
        log("estimation" + validationConfigCopy.getEstimation());
    }

    private void log(String msg){
        logger.info(prefix + msg + postfix);
    }

}
