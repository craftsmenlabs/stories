package org.craftsmenlabs.stories.api.models.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.craftsmenlabs.stories.api.models.Rating;

@Data
@Builder
@AllArgsConstructor
public class ScorableSummary implements Summarizable<ScorableSummary>{
    private float pointsValuation;
    private Rating rating;
    private long violationCount;

    public ScorableSummary() {
        pointsValuation = 0;
        rating = Rating.FAIL;
        violationCount = 0;
    }

    @Override
    public ScorableSummary divideBy(int denominator) {
        return ScorableSummary.builder()
                .pointsValuation(  denominator > 0 ? this.getPointsValuation()/ denominator : 0 )
                .violationCount(  denominator > 0 ? this.getViolationCount() / denominator : 0 )
                .build();
    }

    @Override
    public ScorableSummary plus(ScorableSummary that) {
        return ScorableSummary.builder()
                .pointsValuation(this.getPointsValuation() + that.getPointsValuation())
                .violationCount( this.getViolationCount()  + that.getViolationCount() )
                .build();
    }
}