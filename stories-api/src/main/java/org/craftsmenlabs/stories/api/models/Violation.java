package org.craftsmenlabs.stories.api.models;

public interface Violation
{
	ViolationType getViolationType();

	String getCause();
}
