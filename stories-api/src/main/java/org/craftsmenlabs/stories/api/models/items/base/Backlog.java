package org.craftsmenlabs.stories.api.models.items.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Backlog implements Scorable {
    @JsonProperty("issues")
    private List<? extends BacklogItem> items = new ArrayList<>();
}
