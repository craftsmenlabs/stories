package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Assigns points to acceptance criteria, based on the
 * application of the gherkin language
 */
public class AcceptanceCriteriaScorer {

    public static final int MINIMUM_LENGTH_OF_ACC_CRITERIA = 20;
    private static final float LENGTH_POINTS = 0.25f;
    private static final float GIVEN_POINTS = 0.25f;
    private static final float WHEN_POINTS = 0.25f;
    private static final float THEN_POINTS = 0.25f;
    private static final float TOTAL_POINTS = GIVEN_POINTS + WHEN_POINTS + THEN_POINTS + LENGTH_POINTS;

    public static ValidatedAcceptanceCriteria performScorer(String criteria, ValidationConfig validationConfig) {

        List<Violation> violations = new ArrayList<>();
        float points = 0f;

        if (criteria == null || criteria.isEmpty()) {
            criteria = "";
            violations.add(new Violation(ViolationType.CriteriaEmptyViolation,
                    "No acceptance criteria were found.",
                    1f));
        }

        final String criteriaLower = criteria.toLowerCase();

        List<String> givenWords = validationConfig.getCriteria().getGivenKeywords() != null ? validationConfig.getCriteria().getGivenKeywords() : Collections.emptyList();
        if (givenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += GIVEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaFormatViolation,
                    "<Given> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", givenWords), GIVEN_POINTS));
        }

        List<String> whenWords = validationConfig.getCriteria().getWhenKeywords() != null ? validationConfig.getCriteria().getWhenKeywords() : Collections.emptyList();
        if (whenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += WHEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaFormatViolation,
                    "<When> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", whenWords), WHEN_POINTS));

        }

        List<String> thenWords = validationConfig.getCriteria().getThenKeywords() != null ? validationConfig.getCriteria().getThenKeywords() : Collections.emptyList();
        if (thenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += THEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaFormatViolation,
                    "<Then> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", thenWords), THEN_POINTS));
        }

        if (criteria.length() >= MINIMUM_LENGTH_OF_ACC_CRITERIA) {
            points += LENGTH_POINTS;
        }else{
            violations.add(new Violation(
                    ViolationType.CriteriaFormatViolation,
                    "The criteria should contain a minimum length of " + MINIMUM_LENGTH_OF_ACC_CRITERIA + " characters. " +
                            "It now contains " + criteria.length() + " characters.",
                    LENGTH_POINTS));

        }

        points = points / TOTAL_POINTS;
        Rating rating = points >= validationConfig.getCriteria().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return ValidatedAcceptanceCriteria
                .builder()
                .item(criteria)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .build();
    }
}
