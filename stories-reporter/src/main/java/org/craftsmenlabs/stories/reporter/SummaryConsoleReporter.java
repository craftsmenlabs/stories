package org.craftsmenlabs.stories.reporter;

import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.api.models.summary.Summary;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;

public class SummaryConsoleReporter implements Reporter {
    private final StorynatorLogger logger;

    public SummaryConsoleReporter(StorynatorLogger logger) {
        this.logger = logger;
    }

    @Override
    public void report(StoriesRun storiesRun) {
        BacklogValidatorEntry backlogValidatorEntry = storiesRun.getBacklogValidatorEntry();
        Summary summary = new SummaryBuilder().build(backlogValidatorEntry);

        logger.info(summary.toString());
    }
}
