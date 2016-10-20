package org.craftsmenlabs.stories.api.models;

import lombok.Value;

@Value
public class CriteriaViolation implements Violation
{
	private ViolationType violationType;
	private String cause;
}
