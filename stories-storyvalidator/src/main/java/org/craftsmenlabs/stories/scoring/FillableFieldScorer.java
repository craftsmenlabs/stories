package org.craftsmenlabs.stories.scoring;

import org.apache.commons.lang3.StringUtils;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Bug;
import org.craftsmenlabs.stories.api.models.scrumitems.Epic;
import org.craftsmenlabs.stories.api.models.scrumitems.ScrumItem;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogItem;
import org.craftsmenlabs.stories.api.models.validatorentry.BugValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.EpicValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Assigns points to a bug, based on the rate of which fields are filled
 */
public class FillableFieldScorer<T extends ScrumItem> {

    List<String> enabledFields;
    ValidationConfig validationConfig;
    Map<String, String> fields;

    public FillableFieldScorer(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    public BacklogItem performScorer(T scrumItem) {
        BacklogItem entry = validatorEntryBuilder(scrumItem);

        // If no fields are type, score 0 and FAIL. (otherwise we will get /0)
        if (enabledFields == null || enabledFields.size() == 0) {
            entry.setRating(Rating.FAIL);
            entry.setPointsValuation(0f);
            entry.getViolations().add(new Violation(
                    ViolationType.NoFillableFieldsViolation,
                    "There were no fillable fields defined!",
                    0f,
                    1f
            ));
            return entry;
        }

        float pointsPerField = 1f / enabledFields.size();

        final List<Violation> violations = enabledFields.stream()
                .filter(field -> StringUtils.isBlank(fields.get(field)))
                .map(field -> new Violation(
                        ViolationType.FieldEmptyViolation,
                        "Field " + fields.get(field) + " was found to be empty while it should be filled.",
                        pointsPerField)
                ).collect(Collectors.toList());

        entry.setViolations(violations);
        entry.setPointsValuation((float) (1f - violations.stream().mapToDouble(violation -> (double)violation.getPotentialPoints()).sum()));
        entry.setRating(getRating(entry));
        return entry;
    }

    private Rating getRating(BacklogItem entry) {
        float ratingThreshold;
        if(entry instanceof BugValidatorEntry) {
            ratingThreshold = validationConfig.getBug().getRatingThreshold();
        }else if(entry instanceof EpicValidatorEntry){
            ratingThreshold = validationConfig.getEpic().getRatingThreshold();
        }else{
            ratingThreshold = 1f;
        }
        return entry.getPointsValuation() >= ratingThreshold ? Rating.SUCCESS : Rating.FAIL;

    }

    private BacklogItem validatorEntryBuilder(T scrumItem){
        BacklogItem entry;
        if(scrumItem instanceof Epic){
            Epic epic = (Epic)scrumItem;
            entry = EpicValidatorEntry.builder()
                    .violations(new LinkedList<>())
                    .epic(epic)
                    .build();
            enabledFields = validationConfig.getEpic().getEnabledFields();

            fields = new HashMap<String, String>(){{
                put("goal", epic.getGoal());
            }};
        }else if(scrumItem instanceof Bug){
            Bug bug = (Bug) scrumItem;
            entry = BugValidatorEntry.builder()
                    .violations(new LinkedList<>())
                    .bug(bug)
                    .build();
            enabledFields = validationConfig.getBug().getEnabledFields();
            fields = new HashMap<String, String>(){
                {
                    put("priority", bug.getPriority());
                    put("reproduction", bug.getReproductionPath());
                    put("software", bug.getSoftware());
                    put("expected", bug.getExpectedBehavior());
                    put("acceptation", bug.getAcceptationCriteria());
                }};
        }else{
            entry = null;
        }

        checkNotAllowedFields();

        return entry;
    }

    /**
     * Throws exception if not allowed fields are defined
     */
    private void checkNotAllowedFields() {
        final List<String> notAllowedFields = enabledFields.stream()
                .filter(enabledField -> fields.keySet().stream()
                        .noneMatch(s -> s.equals(enabledField)))
                .collect(Collectors.toList());
        if(!notAllowedFields.isEmpty()){
            throw new StoriesException("Enabled field(s) " + notAllowedFields.stream().collect(Collectors.joining(", ")) + " don't exist! Please select one or " +
                    "multiple of the following: " +
                    "\"priority\", \"reproduction\", \"software\", \"expected\", \"acceptation\"");
        }
    }

}
