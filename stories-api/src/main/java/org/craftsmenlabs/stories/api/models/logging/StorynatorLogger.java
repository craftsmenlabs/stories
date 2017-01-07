package org.craftsmenlabs.stories.api.models.logging;

public interface StorynatorLogger {
    void info(String message);

    void debug(String message);

    void warn(String message);

    void error(String message);

    void error(String message, Throwable t);
}
