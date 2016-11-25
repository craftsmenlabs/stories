package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.validatorentry.*;

import static org.craftsmenlabs.stories.api.models.Rating.FAIL;
import static org.craftsmenlabs.stories.api.models.Rating.SUCCESS;

public class SummaryBuilder {

    public Summary build(BacklogValidatorEntry entry){

        BacklogItemList<FeatureValidatorEntry> issues = entry.getFeatureValidatorEntries();
        BacklogItemList<BugValidatorEntry> bugs = entry.getBugValidatorEntries();
        BacklogItemList<EpicValidatorEntry> epics = entry.getEpicValidatorEntries();

        return Summary.builder()
                .backlog(Summary.ScorableSummary.builder()
                        .pointsValuation(entry.getPointsValuation())
                        .rating(entry.getRating())
                        .build())
                .features(getCount(issues))
                .bugs(getCount(bugs))
                .epics(getCount(epics))
                .build();
    }

    public static Summary.BacklogItemListSummary getCount(BacklogItemList<? extends BacklogItem> list) {
        if(list.getItems() == null){
            return Summary.BacklogItemListSummary.builder()
                    .failed(0)
                    .passed(0)
                    .build();
        }

        return Summary.BacklogItemListSummary.builder()
                .failed(list.getItems().stream().filter(o -> o.getRating() == FAIL).count())
                .passed(list.getItems().stream().filter(o -> o.getRating() == SUCCESS).count())
                .build();
    }
}
