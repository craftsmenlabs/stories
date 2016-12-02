package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Bug;
import org.craftsmenlabs.stories.api.models.validatorentry.BugValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.LinkedList;
import java.util.List;

/**
 * Assigns points to a bug, based on the rate of which fields are filled
 */
public class BugScorer {

    public static BugValidatorEntry performScorer(Bug bug, ValidationConfig validationConfig) {
        BugValidatorEntry entry = BugValidatorEntry.builder()
                .violations(new LinkedList<>())
                .bug(bug)
                .build();

        List<String> enabledFields = validationConfig.getBug().getEnabledFields();

        // If no fields are enabled, score 0 and FAIL. (otherwise we will get /0)
        if (enabledFields == null || enabledFields.size() == 0) {
            entry.setRating(Rating.FAIL);
            entry.setPointsValuation(0f);
            entry.getViolations().add(new Violation(ViolationType.NoFillableFieldsViolation, "There were no fillable fields defined!"));
            return entry;
        }

        float pointsPerField = 1f / enabledFields.size();
        float totalPoints = 0;

        for (String field : enabledFields) {
            String fieldValue = getField(field, bug);

            if (StringUtils.isNotBlank(fieldValue)) {
                totalPoints += pointsPerField;
            } else {
                entry.getViolations().add(new Violation(ViolationType.BugFieldEmptyViolation, "Field " + field + " was found to be empty while it should be filled."));
            }
        }

        entry.setPointsValuation(totalPoints);
        entry.setRating(totalPoints >= validationConfig.getBug().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL);
        return entry;
    }

    private static String getField(String field, Bug bug) {
        switch (field) {
            case "priority":
                return bug.getPriority();
            case "reproduction":
                return bug.getReproductionPath();
            case "software":
                return bug.getSoftware();
            case "expected":
                return bug.getExpectedBehavior();
            case "acceptation":
                return bug.getAcceptationCriteria();
            default:
                throw new StoriesException("Enabled field " + field + " does not exist! Please select one or " +
                        "multiple of the following: " +
                        "\"priority\", \"reproduction\", \"software\", \"expected\", \"acceptation\"");
        }
    }

}
