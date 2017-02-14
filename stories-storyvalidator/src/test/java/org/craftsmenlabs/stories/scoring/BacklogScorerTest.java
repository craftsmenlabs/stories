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
import org.craftsmenlabs.stories.api.models.items.types.Scorable;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedFeature;
import org.craftsmenlabs.stories.ranking.Ranking;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"Duplicates", "ResultOfMethodCallIgnored"})
public class BacklogScorerTest {
    @Mocked
    private Backlog backlog;

    @Mocked
    private ValidationConfig validationConfig;

    @Mocked
    private Map<Class<? extends Scorable>, AbstractScorer> scorers;


    @Injectable
    private Ranking ranking;

    private BacklogScorer getScorer(ValidationConfig validationConfig, Ranking ranking) {
        return new BacklogScorer(validationConfig, ranking);
    }

    @Test
    public void testPerformScorerReturnsSuccesOnScoreExactlyOnThreshold() throws Exception {

        Feature feature = new Feature();
        Map<String, Feature> features = Collections.singletonMap("1", feature);
        final ValidatedFeature validatedFeature = ValidatedFeature.builder().scoredPoints(1).build();
        final BacklogScorer backlogScorer = getScorer(validationConfig, ranking);

        backlogScorer.setScorers(scorers);

        new Expectations() {{
            backlog.getIssues();
            result = features;

            ranking.getRanking(new ArrayList<>(features.values()));
            result = Arrays.asList(1.0);

            validationConfig.getFeature().isActive();
            result = true;

            validationConfig.getBacklog().getRatingThreshold();
            result = 0;

            scorers.get(Feature.class).validate(feature);
            result = validatedFeature;
        }};


        ValidatedBacklog result = backlogScorer.validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.SUCCESS);
    }

    @Test
    public void testPerformScorerReturnsFailonZeroBacklogScore() throws Exception {
        Map<String, Feature> features = Collections.singletonMap("1", new Feature());

        new Expectations() {{
            backlog.getIssues();
            result = features;

            ranking.getRanking(new ArrayList<>(features.values()));
            result = Arrays.asList(1.0);

            validationConfig.getFeature().isActive();
            result = true;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50;
        }};

        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
        assertThat(result.getScoredPoints()).isEqualTo(0);
    }

    @Test
    public void testBacklogAndFeatureRatingIsSameWhenOnlyFeaturesAreEnabled() {
        Map<String, Feature> features = Collections.singletonMap("1", new Feature());

        new Expectations() {{
            backlog.getIssues();
            result = features;

            ranking.getRanking(new ArrayList<>(features.values()));
            result = Arrays.asList(1.0);
            maxTimes = 2;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50;

            validationConfig.getFeature().isActive();
            result = true;
        }};

        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testBacklogAndBugRatingIsSameWhenOnlyBugsAreEnabled() {
        Map<String, Bug> bugs = Collections.singletonMap("1", new Bug());

        new Expectations() {{
            backlog.getIssues();
            result = bugs;

            ranking.getRanking(new ArrayList<>(bugs.values()));
            result = Arrays.asList(1.0);
            maxTimes = 2;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50;
            validationConfig.getBug().isActive();
            result = true;
        }};


        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testBacklogAndEpicRatingIsSameWhenOnlyEpicsAreEnabled() {
        Map<String, Epic> epics = Collections.singletonMap("1", new Epic());

        new Expectations() {{
            backlog.getIssues();
            result = epics;

            ranking.getRanking(new ArrayList<>(epics.values()));
            result = Arrays.asList(1.0);
            maxTimes = 2;

            validationConfig.getBacklog().getRatingThreshold();
            result = 50;
            validationConfig.getEpic().isActive();
            result = true;
        }};


        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(backlog);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);
    }

    @Test
    public void testPerformScorerFailOnEmptyBacklog(@Injectable Ranking ranking) {
        ValidatedBacklog result = getScorer(validationConfig, ranking).validate(null);

        assertThat(result.getScoredPoints()).isEqualTo(0.0);
        assertThat(result.getRating()).isEqualTo(Rating.FAIL);

    }
}
