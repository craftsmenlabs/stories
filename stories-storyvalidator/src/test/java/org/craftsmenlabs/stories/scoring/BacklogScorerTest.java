package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.ranking.Ranking;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BacklogScorerTest {
    @Mocked
    private Backlog backlog;

    @Mocked
    private ValidationConfig validationConfig;

    @Injectable
    private Ranking ranking;

    private BacklogScorer getScorer(ValidationConfig validationConfig, Ranking ranking) {
        return new BacklogScorer(validationConfig, ranking);
    }

    @Test
    public void testPerformScorerReturnsSuccesOnScoreExactlyOnThreshold() throws Exception {
        List<Feature> features = Arrays.asList(
                new Feature()
        );

        new Expectations() {{
            backlog.getItems();
            result = features;

            ranking.createRanking(withNotNull());
            result = 0.5f;

            validationConfig.getFeature().isActive();
            result = true;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50f;
        }};

        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.SUCCESS);
    }

    @Test
    public void testPerformScorerReturnsFailonZeroBacklogScore() throws Exception {
        List<Feature> features = Arrays.asList(
                new Feature()
        );

        new Expectations() {{
            backlog.getItems();
            result = features;

            ranking.createRanking(withNotNull());
            result = 0f;

            validationConfig.getFeature().isActive();
            result = true;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50f;
        }};

        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testBacklogAndFeatureRatingIsSameWhenOnlyFeaturesAreEnabled() {
        List<Feature> features = Collections.singletonList(new Feature());

        new Expectations() {{
            backlog.getItems();
            result = features;

            ranking.createRanking(withNotNull());
            result = 0.4f;
            maxTimes = 2;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50f;
            validationConfig.getFeature().isActive();
            result = true;
        }};

        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testBacklogAndBugRatingIsSameWhenOnlyBugsAreEnabled() {
        List<Bug> bugs = Collections.singletonList(new Bug());

        new Expectations() {{
            backlog.getItems();
            result = bugs;

            ranking.createRanking(withNotNull());
            result = 0.4f;
            maxTimes = 2;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50f;
            validationConfig.getBug().isActive();
            result = true;
        }};


        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testBacklogAndEpicRatingIsSameWhenOnlyEpicsAreEnabled() {
        List<Epic> epics = Collections.singletonList(new Epic());

        new Expectations() {{
            backlog.getItems();
            result = epics;

            ranking.createRanking(withNotNull());
            result = 0.4f;
            maxTimes = 2;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50f;
            validationConfig.getEpic().isActive();
            result = true;
        }};


        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerFailOnEmptyBacklog(@Injectable Ranking ranking) {
        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(null);

        assertThat(result.getPointsValuation()).isEqualTo(0f);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);

    }
}
