package org.craftsmenlabs.stories.api.models.items.types;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.violation.Violation;

import java.util.List;

public abstract class AbstractScorable implements Scorable {
    private float pointsValuation = 0.0f;

    @JsonProperty("violations")
    private List<Violation> violations;

    @JsonProperty("rating")
    private Rating rating;

    public AbstractScorable() {
    }

    public AbstractScorable(float pointsValuation, List<Violation> violations, Rating rating) {
        this.pointsValuation = pointsValuation;
        this.violations = violations;
        this.rating = rating;
    }

    @Override
    public float getPointsValuation() {
        return pointsValuation;
    }

    @Override
    public void setPointsValuation(float pointsValuation) {
        this.pointsValuation = pointsValuation;
    }

    @Override
    public List<Violation> getViolations() {
        return violations;
    }

    @Override
    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
