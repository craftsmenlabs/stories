package org.craftsmenlabs.stories.api.models.items.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BacklogItem implements Rankable, Scorable {
    private String key;
    private String rank;
    private String externalURI;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
