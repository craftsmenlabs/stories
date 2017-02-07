package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estimation implements Scorable{
    private float estimation;

    public static Estimation empty(){
        return new Estimation(0f);
    }
}
