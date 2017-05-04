package org.craftsmenlabs.stories.api.models.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorynatorConfig {
    private SourceConfig source;
    private ReportConfig report;
    private ValidationConfig validation;
    private FilterConfig filter;
    private FieldMappingConfig fieldMapping;

    //used in the gareth dashboard
    @SuppressWarnings("unused")
    public static StorynatorConfig createDefault() {
        StorynatorConfig config = StorynatorConfig.builder()
                .fieldMapping(FieldMappingConfig.createDefault())
                .validation(ValidationConfig.createDefault())
                .source(SourceConfig.createDefault())
                .filter(FilterConfig.createDefault())
                .build();

        return config;
    }
}
