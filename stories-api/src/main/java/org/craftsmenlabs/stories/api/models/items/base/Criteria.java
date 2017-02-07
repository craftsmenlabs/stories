package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Criteria implements Scorable {
    private String criteria;

    public static Criteria empty(){
        return new Criteria("");
    }
}
