package org.craftsmenlabs.stories.api.models.scrumitems;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Issue implements ScrumItem{
    private String key;
    private String rank;
    private String userstory;
    private String acceptanceCriteria;
    private Float estimation;
}