package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.base.Epic;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedEpic;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.LinkedList;
import java.util.List;

/**
 * Assigns points to a bug, based on the rate of which fields are filled
 */
public class EpicScorer extends AbstractScorer<Epic, ValidatedEpic> {

    public EpicScorer(ValidationConfig validationConfig) {
        super(validationConfig);
    }

    @Override
    public ValidatedEpic validate(Epic epic) {
        ValidatedEpic entry = ValidatedEpic.builder()
                .violations(new LinkedList<>())
                .epic(epic)
                .build();

        List<String> enabledFields = validationConfig.getEpic().getEnabledFields();

        // If no fields are type, score 0 and FAIL. (otherwise we will get /0)
        if (enabledFields == null || enabledFields.size() == 0) {
            entry.setRating(Rating.FAIL);
            entry.setPointsValuation(0f);
            entry.getViolations().add(new Violation(ViolationType.NoFillableFieldsViolation, "There were no fillable fields defined!"));
            return entry;
        }

        float pointsPerField = 1f / enabledFields.size();
        float totalPoints = 0;

        for (String field : enabledFields) {
            String fieldValue = getField(field, epic);

            if (StringUtils.isNotBlank(fieldValue)) {
                totalPoints += pointsPerField;
            } else {
                entry.getViolations().add(new Violation(ViolationType.BugFieldEmptyViolation, "Field " + field.substring(0, 1).toUpperCase() + field.replace("_", " ").substring(1) + " was found to be empty while it should be filled."));
            }
        }

        entry.setPointsValuation(totalPoints);
        entry.setRating(totalPoints >= validationConfig.getEpic().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL);
        return entry;
    }

    private String getField(String field, Epic epic) {
        switch (field) {
            case "goal":
                return epic.getGoal();
            default:
                throw new StoriesException("Enabled field " + field + " does not exist! Please select one or " +
                        "multiple of the following: " +
                        "\"goal\"");
        }
    }

}
