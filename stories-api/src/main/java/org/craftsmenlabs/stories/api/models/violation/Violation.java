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

	private float scoredPercentage;
	private float missedPercentage;
	private float scoredPoints;
	private float missedPoints;

	public Violation(ViolationType violationType, String cause, float missedPercentage, float potentialPoints) {
		this.violationType = violationType;
		this.cause = cause;
		setPoints(missedPercentage, potentialPoints);
	}

	//shortcut in case of missed all the points
	public Violation(ViolationType violationType, String cause, float potentialPoints) {
		this.violationType = violationType;
		this.cause = cause;
		setPoints(1f, potentialPoints);
	}

	public void setPoints(float missedPercentage, float potentialPoints) {
		this.scoredPercentage = 0f;
		this.missedPercentage = missedPercentage;

		this.scoredPercentage = 1f - missedPercentage;
		this.missedPercentage = missedPercentage;
		this.scoredPoints = scoredPercentage * potentialPoints;
		this.missedPoints = missedPercentage * potentialPoints;
	}

}
