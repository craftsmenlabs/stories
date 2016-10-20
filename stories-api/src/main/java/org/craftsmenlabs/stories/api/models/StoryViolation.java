package org.craftsmenlabs.stories.api.models;

import lombok.Value;

@Value
public class StoryViolation implements Violation
{
	ViolationType violationType;
	String cause;
}
