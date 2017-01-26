package org.craftsmenlabs.stories.reporter;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesReport;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.*;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ConsoleReporter implements Reporter {
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
    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private final DecimalFormat doubleDecimalFormat = new DecimalFormat("#.##");
    private final StorynatorLogger logger;
    private String prefix = ANSI_PURPLE;
    private ValidationConfig validationConfig;
    private String postfix = ANSI_RESET;
    private String storynator =
            "   ,d88~/\\   d8                                                d8                   \n" +
                    "   8888/   _d88__  e88~-_  888-~\\ Y88b  / 888-~88e   /~~~8e  _d88__  e88~-_  888-~\\ \n" +
                    "   `Y88b    888   d888   i 888     Y888/  888  888       88b  888   d888   i 888    \n" +
                    "    `Y88b,  888   8888   | 888      Y8/   888  888  e88~-888  888   8888   | 888    \n" +
                    "     /8888  888   Y888   ' 888       Y    888  888 C888  888  888   Y888   ' 888    \n" +
                    "   \\/_88P'  \"88_/  \"88_-~  888      /     888  888  \"88_-888  \"88_/  \"88_-~  888    \n" +
                    "                                  _/";

    public ConsoleReporter(StorynatorLogger logger, ValidationConfig validationConfig) {
        this.logger = logger;
        this.validationConfig = validationConfig;
    }

    public void report(StoriesReport storiesReport) {
        ValidatedBacklog validatedBacklog = storiesReport.getValidatedBacklog();
        //header
        Random r = new Random();
        String stry = storynator.chars().mapToObj(chr -> "" + COLORS[r.nextInt(COLORS.length)] + (char) chr + ANSI_RESET).collect(Collectors.joining(""));

        log("\n\n\n" + stry + " \n\n\n");
        log("------------------------------------------------------------");
        log("--           Validator configuration                      --");
        log("------------------------------------------------------------");
        reportOnConfig(validationConfig);

        log("------------------------------------------------------------");
        log("--               verbose output                           --");
        log("------------------------------------------------------------");

        //verbose output
        List<ValidatedFeature> features = validatedBacklog.getValidatedFeatures();
        if (features != null) {
            features.forEach(this::reportOnIssue);
        }
        // Log bugs

        List<ValidatedBug> bugs = validatedBacklog.getValidatedBugs();
        if (bugs != null) {
            bugs.forEach(this::reportOnBug);
        }

        // Log epics
        List<ValidatedEpic> epics = validatedBacklog.getValidatedEpics();
        if (epics != null) {
            epics.forEach(this::reportOnEpic);
        }

        // Log epics
        List<ValidatedTeamTask> teamTasks = validatedBacklog.getValidatedTeamTasks();
        if (teamTasks != null) {
            teamTasks.forEach(this::reportOnTeamTask);
        }

        //show backlog violations
        log("------------------------------------------------------------");
        validatedBacklog.getViolations().forEach(violation -> log(violation.toString()));

        //Summary
        prefix = validatedBacklog.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;
        log("\n\n\n" + stry + " \n\n\n");
        log("------------------------------------------------------------");
        log("--                  Storynator report                     --");
        log("------------------------------------------------------------");

        log("Processed a total of " + features.size() + " user stories, " + bugs.size() + " bugs and " + epics.size() + " epics\r\n");


        log("\r\n");
        log("Those three combined result in a score of "
                + doubleDecimalFormat.format(validatedBacklog.getPointsValuation() * 100f)
                + " / "
                + MAX_SCORE);
        log("Rated: " + validatedBacklog.getRating() + "  (with threshold on: " + validationConfig.getBacklog()
                .getRatingThreshold() + ")");
    }

    public void reportOnIssue(ValidatedFeature issue) {
        prefix = issue.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;

        log("------------------------------------------------------------");
        log("Issue "
                + issue.getItem().getKey()
                + " Item total ("
                + decimalFormat.format(issue.getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ") \t"
                + " US ("
                + decimalFormat.format(issue.getValidatedUserStory().getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ")\t"
                + " AC ("
                + decimalFormat.format(issue.getValidatedAcceptanceCriteria().getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ")\t"
        );
        log(issue.getItem().getSummary());
        issue.getViolations()
                .forEach(violation ->
                        log("Violation found:" + violation.toString()));

        reportOnUserstory(issue.getValidatedUserStory());
        reportOnAcceptanceCriteria(issue.getValidatedAcceptanceCriteria());
        reportOnEstimation(issue.getValidatedEstimation());
    }

    public void reportOnBug(ValidatedBug bug) {
        prefix = bug.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;
        log("------------------------------------------------------------");
        log("Bug "
                + bug.getItem().getKey()
                + " Item total ("
                + decimalFormat.format(bug.getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ") \t"
        );
        log(bug.getItem().getSummary());
        reportOnViolations(bug.getViolations());
    }

    public void reportOnEpic(ValidatedEpic epic) {
        prefix = epic.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;
        log("------------------------------------------------------------");
        log("Epic "
                + epic.getItem().getKey()
                + " Item total ("
                + decimalFormat.format(epic.getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ") \t"
        );
        log(epic.getItem().getSummary());
        reportOnViolations(epic.getViolations());
    }

    public void reportOnTeamTask(ValidatedTeamTask teamTask) {
        prefix = teamTask.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;
        log("------------------------------------------------------------");
        log("teamTask "
                + teamTask.getItem().getKey()
                + " Item total ("
                + decimalFormat.format(teamTask.getPointsValuation() * 100)
                + "/"
                + MAX_SCORE
                + ") \t"
        );
        log("Summary: " + teamTask.getItem().getSummary());
        log("Description: " + teamTask.getItem().getDescription());
        log("Criteria: " + teamTask.getItem().getAcceptationCriteria());
        log("Estimation: " + Float.toString(teamTask.getItem().getEstimation()));
        reportOnViolations(teamTask.getViolations());
    }

    public void reportOnUserstory(ValidatedUserStory entry) {
        prefix = entry.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;


        String userstory = entry.getItem().replace("\n", " ").replace("\r", "");
        log("\t Userstory: (" + entry.getPointsValuation() + ") " + userstory);

        reportOnViolations(entry.getViolations());
    }

    public void reportOnAcceptanceCriteria(ValidatedAcceptanceCriteria entry) {
        prefix = entry.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;

        String criteria = entry.getItem().replace("\n", " ").replace("\r", "");
        log("\t Criteria: (" + entry.getPointsValuation() + ") " + criteria);

        reportOnViolations(entry.getViolations());
    }

    public void reportOnEstimation(ValidatedEstimation entry) {
        prefix = entry.getRating() == Rating.SUCCESS ? ANSI_GREEN : ANSI_RED;

        Float estimation = entry.getItem();
        log("\t Estimation: (" + entry.getPointsValuation() + ")" + estimation);

        reportOnViolations(entry.getViolations());
    }

    public void reportOnViolations(List<Violation> violations) {
        prefix = ANSI_BLUE;
        violations.forEach(violation ->
                log("\t\t Violation found: " + violation.toString()));
    }

    private void reportOnConfig(ValidationConfig validationConfig) {
        log("backlog: " + validationConfig.getBacklog().toString());
        log("issues: " + validationConfig.getFeature());
        log("stories: " + validationConfig.getStory());
        log("criteria: " + validationConfig.getCriteria());
        log("estimation: " + validationConfig.getEstimation());
        log("bugs: " + validationConfig.getBug());
        log("epics: " + validationConfig.getEpic());
        log("team task: " + validationConfig.getTeamTask());
    }

    private void log(String msg) {
        logger.info(prefix + msg + postfix);
    }

}
