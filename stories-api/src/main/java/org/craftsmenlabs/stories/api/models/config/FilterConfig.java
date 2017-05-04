package org.craftsmenlabs.stories.api.models.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterConfig {
    private String status;

    public static FilterConfig createDefault() {
        return FilterConfig.builder().status("To Do").build();
    }
}
