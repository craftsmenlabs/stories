package org.craftsmenlabs.stories.api.models.items.types;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class BacklogItemList<T extends ValidatedBacklogItem> {
    private List<T> items = new ArrayList<>();
    private boolean isActive = false;

    @SuppressWarnings("unused")
    @ConstructorProperties({"items", "isActive"})
    public BacklogItemList(List<T> items, boolean isActive) {
        this.items = items;
        this.isActive = isActive;
    }
}
