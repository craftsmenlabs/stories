package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.*;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;
import org.craftsmenlabs.stories.api.models.items.types.Scorable;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;
import org.craftsmenlabs.stories.ranking.Ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BacklogScorer extends AbstractScorer<Backlog, ValidatedBacklog> {
    private Ranking ranking;
    private Map<Class<? extends Scorable>, AbstractScorer> scorers = new HashMap<>();

    public BacklogScorer(ValidationConfig validationConfig, Ranking ranking) {
        super(validationConfig);
        this.ranking = ranking;

        scorers.put(Bug.class, new BugScorer(validationConfig));
        scorers.put(Epic.class, new EpicScorer(validationConfig));
        scorers.put(TeamTask.class, new TeamTaskScorer(validationConfig));
        scorers.put(Feature.class, new FeatureScorer(validationConfig));
    }


    @Override
    public ValidatedBacklog validate(Backlog backlog) {
        ValidatedBacklog validatedBacklog = ValidatedBacklog.builder()
                .backlog(backlog)
                .items(new ArrayList<>())
                .violations(new ArrayList<>())
                .build();


        if (backlog == null || backlog.getIssues().size() == 0) {
            validatedBacklog.getViolations().add(new Violation(
                    ViolationType.BacklogEmptyViolation,
                    "The backlog is empty, or doesn't contain any issues."
            ));

            validatedBacklog.setPointsValuation(0f);
            validatedBacklog.setRating(Rating.FAIL);
            return validatedBacklog;
        }

        List<ValidatedBacklogItem> validatedItems = backlog.getIssues().entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(this::isIssueActive)
                .map(item -> scorers.get(item.getClass()).validate(item))
                .map(item -> (ValidatedBacklogItem) item)
                .collect(Collectors.toList());

        float backlogPoints = ranking.createRanking(validatedItems);
        validatedBacklog.setPointsValuation(backlogPoints);
        validatedBacklog.setItems(validatedItems);

        if (validatedBacklog.getPointsValuation() * 100f >= validationConfig.getBacklog().getRatingThreshold()) {
            validatedBacklog.setRating(Rating.SUCCESS);
        } else {
            // Failed, add violation
            validatedBacklog.setRating(Rating.FAIL);
            validatedBacklog.getViolations().add(new Violation(
                    ViolationType.BacklogRatingViolation,
                    "The backlog did not score a minimum of " + validationConfig.getBacklog().getRatingThreshold()
                            + " points and is therefore rated: " + validatedBacklog.getRating()));
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
