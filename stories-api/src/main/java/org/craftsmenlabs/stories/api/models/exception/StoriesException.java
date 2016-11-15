package org.craftsmenlabs.stories.api.models.exception;

/**
 * Runtime exception which can be safely caught while executing the application, since we know the origin.
 * Because of this, we only see stacktraces of errors we are not aware of yet.
 */
public class StoriesException extends RuntimeException {
    public StoriesException(String message) {
        super("\u001B[31m" + message);
    }
}
