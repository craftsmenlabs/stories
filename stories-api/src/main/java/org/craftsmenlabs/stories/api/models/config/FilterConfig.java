package org.craftsmenlabs.stories.api.models.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterConfig {
    private String status;
}
