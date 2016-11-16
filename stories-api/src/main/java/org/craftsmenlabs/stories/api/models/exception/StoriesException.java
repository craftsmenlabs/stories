package org.craftsmenlabs.stories.api.models.exception;

/**
 * Runtime exception which can be safely caught while executing the application, since we know the origin.
 * Because of this, we only see stacktraces of errors we are not aware of yet.
 */
public class StoriesException extends RuntimeException {
    public static final String ERR_SOURCE_ENABLED_MISSING = "Parameter source.enabled should be configured with either trello or jira. Please check your config or parameters.";


    public StoriesException(String message) {
        super("\u001B[31m" + message);
    }
}
