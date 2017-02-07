package org.craftsmenlabs.stories.api.models.items.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
public class Feature extends BacklogItem {
    private String summary;
    private Story userstory;
    private Criteria acceptanceCriteria;
    private Estimation estimation;

    public static Feature empty(){
        return Feature.builder().summary("").userstory(new Story("")).acceptanceCriteria(new Criteria("")).estimation(new Estimation(0f)).build();
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

    public Story getUserstory() {
        if(userstory == null){
            return new Story();
        }
        return userstory;
    }

    public void setUserstory(Story userstory) {
        this.userstory = userstory;
    }

    public Criteria getAcceptanceCriteria() {
        if(acceptanceCriteria == null){
            return new Criteria();
        }
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(Criteria acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public Estimation getEstimation() {
        if(estimation == null){
            return new Estimation();
        }
        return estimation;
    }

    public void setEstimation(Estimation estimation) {
        this.estimation = estimation;
    }

    @Builder
    public Feature(String summary, Story userstory, Criteria acceptanceCriteria, Estimation estimation, String key, String rank, String externalURI, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(key, rank, externalURI, updatedAt, createdAt);
        this.summary = summary;
        this.userstory = userstory;
        this.acceptanceCriteria = acceptanceCriteria;
        this.estimation = estimation;
    }
}