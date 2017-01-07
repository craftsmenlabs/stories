package org.craftsmenlabs.stories.api.models.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandaloneLogger implements StorynatorLogger {
    private static final Logger logger = LoggerFactory.getLogger(StorynatorLogger.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String message, Throwable t) {
        logger.error(message, t);
    }
}
