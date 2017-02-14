package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Bug;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklogItem;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBug;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEpic;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Assigns scoredPercentage to a bug, based on the rate of which fields are filled
 */
public class FillableFieldScorer extends AbstractScorer<BacklogItem, ValidatedBacklogItem> {

    private List<String> enabledFields;
    private Map<String, String> fields;

    public FillableFieldScorer(ValidationConfig validationConfig) {
        super(1.0, validationConfig);
    }

    public FillableFieldScorer(double potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
    }



    @Override
    public ValidatedBacklogItem validate(BacklogItem scrumItem) {
        ValidatedBacklogItem entry = validatorEntryBuilder(scrumItem);

        // If no fields are type, score 0 and FAIL. (otherwise we will get /0)
        if (enabledFields == null || enabledFields.size() == 0) {
            entry.setRating(Rating.FAIL);
            entry.setScoredPoints(0.0);
            entry.getViolations().add(new Violation(
                    ViolationType.NoFillableFieldsViolation,
                    "There were no fillable fields defined!",
                    potentialPoints
            ));
            entry.setPoints(0.0, potentialPoints);
            return entry;
        }

        double scorePerField = potentialPoints / enabledFields.size();

        final List<Violation> violations = enabledFields.stream()
                .filter(field -> StringUtils.isBlank(fields.get(field)))
                .map(field -> new Violation(
                        ViolationType.FieldEmptyViolation,
                        "Field " + field + " was found to be empty while it should be filled.",
                        scorePerField,
                        potentialPoints)
                ).collect(Collectors.toList());

        entry.setViolations(violations);
        entry.setPoints((double) (potentialPoints - violations.stream().mapToDouble(violation -> (double) violation.getMissedPercentage()).sum()), potentialPoints);

        entry.setRating(getRating(entry));
        return entry;
    }

    private Rating getRating(ValidatedBacklogItem entry) {
        double ratingThreshold;
        if (entry instanceof ValidatedBug) {
            ratingThreshold = validationConfig.getBug().getRatingThreshold();
        } else if (entry instanceof ValidatedEpic) {
            ratingThreshold = validationConfig.getEpic().getRatingThreshold();
        } else {
            ratingThreshold = 1.0;
        }
        return entry.getScoredPercentage() >= ratingThreshold ? Rating.SUCCESS : Rating.FAIL;

    }

    private ValidatedBacklogItem validatorEntryBuilder(BacklogItem scrumItem) {
        ValidatedBacklogItem entry;
        if (scrumItem instanceof Epic) {
            Epic epic = (Epic) scrumItem;
            entry = ValidatedEpic.builder()
                    .violations(new LinkedList<>())
                    .epic(epic)
                    .build();
            enabledFields = validationConfig.getEpic().getEnabledFields();

            fields = new HashMap<String, String>() {{
                put("goal", epic.getGoal());
            }};
        } else if (scrumItem instanceof Bug) {
            Bug bug = (Bug) scrumItem;
            entry = ValidatedBug.builder()
                    .violations(new LinkedList<>())
                    .bug(bug)
                    .build();
            enabledFields = validationConfig.getBug().getEnabledFields();
            fields = new HashMap<String, String>() {
                {
                    put("priority", bug.getPriority());
                    put("reproduction_path", bug.getReproductionPath());
                    put("environment", bug.getEnvironment());
                    put("expected_behaviour", bug.getExpectedBehavior());
                    put("acceptation_criteria", bug.getAcceptationCriteria());
                }
            };
        } else {
            entry = null;
        }

        checkNotAllowedFields();

        return entry;
    }

    /**
     * Throws exception if not allowed fields are defined
     */
    private void checkNotAllowedFields() {
        if (enabledFields == null) {
            return;
        }
        final List<String> notAllowedFields = enabledFields.stream()
                .filter(enabledField -> fields.keySet().stream()
                        .noneMatch(s -> s.equals(enabledField)))
                .collect(Collectors.toList());
        if (!notAllowedFields.isEmpty()) {
            throw new StoriesException("Enabled field(s) " + notAllowedFields.stream().collect(Collectors.joining(", ")) + " don't exist! Please select one or " +
                    "multiple of the following: " +
                    "\"priority\", \"reproduction_path\", \"environment\", \"expected_behaviour\", \"acceptation_criteria\"");
        }
    }
}
