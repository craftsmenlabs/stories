package org.craftsmenlabs.stories.api.models.violation;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	public Violation(ViolationType violationType, String cause, float potentialPoints) {
		this.violationType = violationType;
		this.cause = cause;
		this.points = 0;
		this.potentialPoints = potentialPoints;
	}

	@JsonProperty("violationType")
	private ViolationType violationType;

	private String cause;

	private float points;
	private float potentialPoints;
}
