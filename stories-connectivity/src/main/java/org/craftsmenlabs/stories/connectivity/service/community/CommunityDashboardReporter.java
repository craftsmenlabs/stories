package org.craftsmenlabs.stories.connectivity.service.community;

import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class CommunityDashboardReporter implements Reporter {

    private final Logger logger = LoggerFactory.getLogger(CommunityDashboardReporter.class);

    @Override
    public void report(StoriesRun storiesRun) {
        logger.info("\n"
                + "***\n"
                + "Thank you for using the community version.");
        logger.info(
                "In order to use the data export service. Please look at our enterprise version. \nContact us at: http://craftsmenlabs"
                        + ".nl/.\n***\n");
    }
}
