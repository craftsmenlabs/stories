package org.craftsmenlabs.stories.reporter;

import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.craftsmenlabs.stories.api.models.summary.Summary;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;

public class SummaryConsoleReporter implements Reporter {
    private final StorynatorLogger logger;

    public SummaryConsoleReporter(StorynatorLogger logger) {
        this.logger = logger;
    }

    @Override
    public void report(StoriesRun storiesRun) {
        ValidatedBacklog validatedBacklog = storiesRun.getValidatedBacklog();
        Summary summary = new SummaryBuilder().build(validatedBacklog);

        logger.info(summary.toString());
    }
}
