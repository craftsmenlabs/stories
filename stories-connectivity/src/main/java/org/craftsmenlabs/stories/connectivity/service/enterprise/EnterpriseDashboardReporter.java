package org.craftsmenlabs.stories.connectivity.service.enterprise;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.config.ReportConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Reports the information to the dashboard
 */
public class EnterpriseDashboardReporter implements Reporter {
    private final Logger logger = LoggerFactory.getLogger(EnterpriseDashboardReporter.class);
    private ReportConfig.DashboardConfig config;

    public EnterpriseDashboardReporter(ReportConfig reportConfig) {
        this.config = reportConfig.getDashboard();
    }

    public void report(StoriesRun storiesRun) {
        if (StringUtils.isNotEmpty(config.getUrl())) {

            if (StringUtils.isNotEmpty(config.getToken())) {
                storiesRun.setProjectToken(config.getToken());
            }

            logger.info("Instantiating dashboard data submitter.");
            logger.info("Authkey:" + config.getToken());
            logger.info("Dashboard url:" + config.getUrl());

            String url = config.getUrl() + "/storiesrun";
            try {
                RestTemplate template = new RestTemplate();
                template.postForEntity(url, storiesRun, String.class);
            } catch (HttpClientErrorException e) {
                logger.error("Failed to connect to "
                        + url
                        + ". StoriesRunPoster returned HTTP code: "
                        + e.getStatusCode().value()
                        + "\n"
                        + e.getResponseBodyAsString());
                abortOnError();
            }
        } else {
            logger.info("Could not send data to external dashboard. No configuration provided");
            logger.error("Could not send data to external dashboard. No configuration provided");
        }
    }

    private void abortOnError() {
        throw new StoriesException("Failed to connect to " + config.getUrl());
    }
}
