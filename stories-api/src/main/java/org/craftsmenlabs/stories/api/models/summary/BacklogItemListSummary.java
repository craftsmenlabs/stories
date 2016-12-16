package org.craftsmenlabs.stories.api.models.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BacklogItemListSummary implements Summarizable<BacklogItemListSummary>{
    private long passed;
    private long failed;
    private long count;

    public BacklogItemListSummary() {
        passed = 0;
        failed = 0;
        count = 0;
    }

    @Override
    public BacklogItemListSummary divideBy(int denominator) {
        if(denominator == 0){
            return new BacklogItemListSummary();
        }
        return BacklogItemListSummary.builder()
                .failed(this.getFailed() / denominator)
                .passed(this.getPassed() / denominator)
                .count( this.getCount() / denominator)
                .build();
    }

    @Override
    public BacklogItemListSummary plus(BacklogItemListSummary that) {
        return BacklogItemListSummary.builder()
                .failed(this.getFailed() + that.getFailed() )
                .passed(this.getPassed() + that.getPassed() )
                .count( this.getCount()  + that.getCount() )
                .build();
    }
}

