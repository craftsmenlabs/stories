package org.craftsmenlabs.stories.api.models.violation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Violation
{
	private ViolationType violationType;

	private String cause;

    private double scoredPercentage;
    private double missedPercentage;
    private double scoredPoints;
    private double missedPoints;

    public Violation(ViolationType violationType, String cause, double missedPercentage, double potentialPoints) {
        this.violationType = violationType;
        this.cause = cause;
		setPoints(missedPercentage, potentialPoints);
	}

	//shortcut in case of missed all the points
    public Violation(ViolationType violationType, String cause, double potentialPoints) {
        this.violationType = violationType;
        this.cause = cause;
        setPoints(1, potentialPoints);
    }

    public void setPoints(double missedPercentage, double potentialPoints) {
        this.scoredPercentage = 0;
        this.missedPercentage = missedPercentage;

        this.scoredPercentage = 1 - missedPercentage;
        this.missedPercentage = missedPercentage;
        this.scoredPoints = scoredPercentage * potentialPoints;
		this.missedPoints = missedPercentage * potentialPoints;
	}

}
