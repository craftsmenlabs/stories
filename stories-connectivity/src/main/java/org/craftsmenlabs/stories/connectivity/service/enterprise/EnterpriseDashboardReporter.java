package org.craftsmenlabs.stories.connectivity.service.enterprise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.config.ReportConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.logging.StorynatorLogger;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Reports the information to the dashboard
 */
public class EnterpriseDashboardReporter implements Reporter {
    private final StorynatorLogger logger;
    private ReportConfig.DashboardConfig config;

    public EnterpriseDashboardReporter(StorynatorLogger logger, ReportConfig reportConfig) {
        this.config = reportConfig.getDashboard();
        this.logger = logger;
    }

    public void report(StoriesRun storiesRun) {
        if (StringUtils.isNotEmpty(config.getUrl())) {

            if (StringUtils.isNotEmpty(config.getToken())) {
                storiesRun.setProjectToken(config.getToken());
            }

            logger.info("Instantiating dashboard data submitter.");
            logger.info("Authkey:" + config.getToken());
            logger.info("Dashboard url:" + config.getUrl());

            String url = config.getUrl() + "/api/import/v1/report";
            try {
                RestTemplate template = new RestTemplate();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    logger.error(objectMapper.writeValueAsString(storiesRun));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
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
