package org.craftsmenlabs.stories.scoring;

import com.codepoetics.protonpack.StreamUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.*;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;
import org.craftsmenlabs.stories.api.models.items.types.Rankable;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.craftsmenlabs.stories.ranking.Ranking;

import java.util.*;
import java.util.stream.Collectors;

public class BacklogScorer extends AbstractScorer<Backlog, ValidatedBacklog> {
    private Ranking ranking;
    private Map<Class<? extends Scorable>, AbstractScorer> scorers = new HashMap<>();

    public BacklogScorer(ValidationConfig validationConfig, Ranking ranking) {
        super(100, validationConfig);
        this.ranking = ranking;

        this.setDefaultScorers();
    }

    public void setScorers(Map<Class<? extends Scorable>, AbstractScorer> scorers) {
        this.scorers = scorers;
    }

    public void setDefaultScorers() {
        scorers.put(Bug.class, new FillableFieldScorer(validationConfig));
        scorers.put(Epic.class, new FillableFieldScorer(validationConfig));
        scorers.put(TeamTask.class, new TeamTaskScorer(validationConfig));
        scorers.put(Feature.class, new FeatureScorer(validationConfig));
    }

    @Override
    public ValidatedBacklog validate(Backlog backlog) {

        //init empty validated backlog
        ValidatedBacklog validatedBacklog = ValidatedBacklog.builder()
                .backlog(backlog)
                .items(new ArrayList<>())
                .violations(new ArrayList<>())
                .build();


        if (backlog == null || backlog.getIssues().size() == 0) {
            validatedBacklog.getViolations().add(new Violation(
                    ViolationType.BacklogEmptyViolation,
                    "The backlog is empty, or doesn't contain any issues.", 1
            ));

            validatedBacklog.setScoredPoints(0.0);
            validatedBacklog.setRating(Rating.FAIL);
            return validatedBacklog;
        }

        //filter the backlog items on active and sort them
        final List<? extends BacklogItem> activeSortedIssues =
                backlog.getIssues().entrySet().stream()
                        .map(Map.Entry::getValue)
                        .filter(this::isIssueActive)
                        .map(item -> (BacklogItem) item)
                        .sorted(Comparator.comparing(Rankable::getRank))
                        .collect(Collectors.toList());

        //add the potential score (from for example the curved ranking) to the backlogItem
        final List<ValidatedBacklogItem> backlogItems = StreamUtils.zip(
                activeSortedIssues.stream(),
                ranking.getRanking(activeSortedIssues).stream(),
                (item, potentialPoints) -> {
                    final AbstractScorer scorer = scorers.get(item.getClass());
                    scorer.setPotentialPoints(potentialPoints);
                    return scorer.validate(item);
                })
                .map(item -> (ValidatedBacklogItem) item)
                .collect(Collectors.toList());

        //calculate the scored backlog points
        final double backlogPoints = backlogItems.stream().mapToDouble(item -> {
                    item.getScoredPoints();
                    return item.getScoredPoints();
                }
            ).sum();

        //set all the values in the validated backlog
        validatedBacklog.setScoredPoints(backlogPoints);
        validatedBacklog.setItems(backlogItems);

        if (validatedBacklog.getScoredPoints() >= validationConfig.getBacklog().getRatingThreshold()) {
            validatedBacklog.setRating(Rating.SUCCESS);
        } else {
            // Failed, add violation
            validatedBacklog.setRating(Rating.FAIL);
            validatedBacklog.getViolations().add(new Violation(
                    ViolationType.BacklogRatingViolation,
                    "The backlog did not score a minimum of " + validationConfig.getBacklog().getRatingThreshold()
                            + " points and is therefore rated: " + validatedBacklog.getRating(), 100.0));
        }
        return validatedBacklog;
    }

    private <T extends BacklogItem> boolean isIssueActive(T issue) {
        switch (issue.getClass().getSimpleName()) {
            case "Bug":
                return validationConfig.getBug().isActive();
            case "Epic":
                return validationConfig.getEpic().isActive();
            case "TeamTask":
                return validationConfig.getTeamTask().isActive();
            case "Feature":
                return validationConfig.getFeature().isActive();
        }

        throw new IllegalStateException("No active config flag for the class: " + issue.getClass().getSimpleName());
    }
}
