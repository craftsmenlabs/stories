package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Assigns points to acceptance criteria, based on the
 * application of the gherkin language
 */
public class AcceptanceCriteriaScorer {

    public static final int MINIMUM_LENGTH_OF_ACC_CRITERIA = 20;
    private static final float GIVEN_POINTS = 0.3333f;
    private static final float WHEN_POINTS = 0.3333f;
    private static final float THEN_POINTS = 0.3333f;
    private static final float TOTAL_POINTS = GIVEN_POINTS + WHEN_POINTS + THEN_POINTS;

    public static AcceptanceCriteriaValidatorEntry performScorer(String criteria, ValidationConfig validationConfig) {

        List<Violation> violations = new ArrayList<>();
        float points = 0f;

        if (criteria == null || criteria.isEmpty()) {
            criteria = "";
            violations.add(new Violation(ViolationType.CriteriaVoidViolation,
                    "No acceptance criteria where found."));
        }

        final String criteriaLower = criteria.toLowerCase();

        List<String> givenWords = validationConfig.getCriteria().getGivenKeywords();
        if (givenWords != null && givenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += GIVEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaGivenClauseViolation,
                    "<Given> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", validationConfig.getCriteria().getGivenKeywords())));
        }

        List<String> whenWords = validationConfig.getCriteria().getWhenKeywords();
        if (whenWords != null && whenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += WHEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaWhenClauseViolation,
                    "<When> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", validationConfig.getCriteria().getWhenKeywords())));

        }

        List<String> thenWords = validationConfig.getCriteria().getThenKeywords();
        if (thenWords != null && thenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += THEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaThenClauseViolation,
                    "<Then> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", validationConfig.getCriteria().getThenKeywords())));
        }

        if (criteria.length() <= MINIMUM_LENGTH_OF_ACC_CRITERIA) {
            violations.add(new Violation(ViolationType.CriteriaLengthViolation,
                    "The criteria should contain a minimum length of " + MINIMUM_LENGTH_OF_ACC_CRITERIA + " characters. " +
                            "It now contains " + criteria.length() + " characters."));

        }

        points /= TOTAL_POINTS;
        Rating rating = points >= validationConfig.getCriteria().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;

        return AcceptanceCriteriaValidatorEntry
                .builder()
                .item(criteria)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .build();
    }
}
