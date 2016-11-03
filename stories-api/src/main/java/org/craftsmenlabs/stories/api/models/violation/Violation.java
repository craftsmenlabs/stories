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
	@JsonProperty("violationType")
	private ViolationType violationType;

	private String cause;
}
