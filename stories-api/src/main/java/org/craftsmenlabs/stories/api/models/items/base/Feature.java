package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feature extends BacklogItem {
    private String summary;
    private String userstory;
    private String acceptanceCriteria;
    private float estimation;

    public static Feature empty(){
        return Feature.builder().summary("").userstory("").acceptanceCriteria("").estimation(0f).build();
    }

    public String getSummary() {
        if(summary == null){
            return "";
        }
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Builder
    public Feature(String summary, String userstory, String acceptanceCriteria, float estimation, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(key, rank, externalURI, updatedAt, createdAt);
        this.summary = summary;
        this.userstory = userstory;
        this.acceptanceCriteria = acceptanceCriteria;
        this.estimation = estimation;
    }
}