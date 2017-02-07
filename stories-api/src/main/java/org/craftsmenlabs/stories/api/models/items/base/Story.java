package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Story implements Scorable{
    private String story;

    public static Story empty(){
        return new Story("");
    }
}
