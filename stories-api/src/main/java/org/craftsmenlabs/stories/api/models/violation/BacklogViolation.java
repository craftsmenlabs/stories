package org.craftsmenlabs.stories.api.models.violation;

import lombok.Value;

@Value
public class BacklogViolation implements Violation {
    private ViolationType violationType;
    private String cause;
}
