package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.AcceptanceCriteriaValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
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

    public static AcceptanceCriteriaValidatorEntry performScorer(String criteria, ScorerConfigCopy validationConfig) {

        List<Violation> violations = new ArrayList<>();
        float points = 0f;

        if (criteria != null && criteria.length() >= MINIMUM_LENGTH_OF_ACC_CRITERIA) {
            final String criteriaLower = criteria.toLowerCase();

            if (validationConfig.getCriteria().getGivenKeywords().stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
                points += 0.3333;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaGivenClauseViolation, ""));
            }

            if (validationConfig.getCriteria().getWhenKeywords().stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
                points += 0.3333;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaWhenClauseViolation, ""));

            }

            if (validationConfig.getCriteria().getThenKeywords().stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
                points += 0.3333;
            } else {
                violations.add(new CriteriaViolation(ViolationType.CriteriaThenClauseViolation, ""));

            }
        }

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
