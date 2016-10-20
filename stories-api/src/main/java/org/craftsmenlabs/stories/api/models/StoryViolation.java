package org.craftsmenlabs.stories.api.models;

import lombok.Value;

@Value
public class StoryViolation implements Violation
{
	private ViolationType violationType;
	private String cause;
}
