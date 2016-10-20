package org.craftsmenlabs.stories.api.models;

import lombok.Value;

@Value
public class CriteriaViolation implements Violation
{
	ViolationType violationType;
	String cause;
}
