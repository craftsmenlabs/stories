package org.craftsmenlabs.stories.api.models.items.types;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

public interface Scorable {
    float getPointsValuation();
    void setPointsValuation(float pointsValuation);

    List<Violation> getViolations();
    void setViolations(List<Violation> violations);

    Rating getRating();
}
