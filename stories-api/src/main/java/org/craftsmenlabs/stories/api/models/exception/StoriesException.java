package org.craftsmenlabs.stories.api.models.exception;

/**
 * Runtime exception which can be safely caught while executing the application, since we know the origin.
 * Because of this, we only see stack traces of errors we are not aware of yet.
 */
public class StoriesException extends RuntimeException {
    public static final String ERR_SOURCE_ENABLED_MISSING = "Parameter source.type should be configured with one of the supported sources. For example: trello, jira, etc. Please check your config or parameters.";


    public StoriesException(String message) {
        super("\u001B[31m" + message + "\u001B[0m");
    }
}
