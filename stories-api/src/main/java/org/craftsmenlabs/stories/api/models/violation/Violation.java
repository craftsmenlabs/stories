package org.craftsmenlabs.stories.api.models.violation;

public interface Violation
{
	ViolationType getViolationType();

	String getCause();

    String toString();
}
