package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.violation.CriteriaViolation;
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

    public static AcceptanceCriteriaValidatorEntry performScorer(String criteria, ValidationConfigCopy validationConfig) {

        List<Violation> violations = new ArrayList<>();
        float points = 0f;

        if (criteria != null && criteria.length() >= MINIMUM_LENGTH_OF_ACC_CRITERIA) {
            final String criteriaLower = criteria.toLowerCase();

            if (validationConfig.getCriteria().getGivenKeywords().stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
                points += GIVEN_POINTS;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaGivenClauseViolation, ""));
            }

            if (validationConfig.getCriteria().getWhenKeywords().stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
                points += WHEN_POINTS;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaWhenClauseViolation, ""));

            }

            if (validationConfig.getCriteria().getThenKeywords().stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
                points += THEN_POINTS;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaThenClauseViolation, ""));

            }
        }

        points /= TOTAL_POINTS;
        Rating rating = points >= validationConfig.getCriteria().getRatingtreshold()? Rating.SUCCES : Rating.FAIL;

        return AcceptanceCriteriaValidatorEntry
                .builder()
                .acceptanceCriteria(criteria)
                .violations(violations)
                .pointsValuation(points)
                .rating(rating)
                .isActive(validationConfig.getCriteria().isActive())
                .build();
    }
}
