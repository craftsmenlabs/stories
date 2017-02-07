package org.craftsmenlabs.stories.scoring;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.config.ValidationConfig;
import org.craftsmenlabs.stories.api.models.items.base.Criteria;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedAcceptanceCriteria;
import org.craftsmenlabs.stories.api.models.violation.Violation;
import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Assigns scoredPercentage to acceptance criteria, based on the
 * application of the gherkin language
 */
public class AcceptanceCriteriaScorer extends AbstractScorer<Criteria, ValidatedAcceptanceCriteria> {

    public static final int MINIMUM_LENGTH_OF_ACC_CRITERIA = 20;

    public AcceptanceCriteriaScorer(float potentialPoints, ValidationConfig validationConfig) {
        super(potentialPoints, validationConfig);
    }

    @Override
    public ValidatedAcceptanceCriteria validate(Criteria criteria) {
        if(criteria == null){
            criteria = Criteria.empty();
        }
        float LENGTH_POINTS = potentialPoints / 4f;
        float GIVEN_POINTS = potentialPoints / 4f;
        float WHEN_POINTS = potentialPoints / 4f;
        float THEN_POINTS = potentialPoints / 4f;

        List<Violation> violations = new ArrayList<>();
        float points = 0f;

        if (criteria.getCriteria() == null || criteria.getCriteria().isEmpty()) {
            violations.add(new Violation(ViolationType.CriteriaVoidViolation,
                    "No acceptance criteria were found.",
                    potentialPoints));

            criteria.setCriteria("");
        }

        final String criteriaLower = criteria.getCriteria().toLowerCase();

        List<String> givenWords = validationConfig.getCriteria().getGivenKeywords() != null ? validationConfig.getCriteria().getGivenKeywords() : Collections.emptyList();
        if (givenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += GIVEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaGivenClauseViolation,
                    "<Given> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", givenWords), GIVEN_POINTS));
        }

        List<String> whenWords = validationConfig.getCriteria().getWhenKeywords() != null ? validationConfig.getCriteria().getWhenKeywords() : Collections.emptyList();
        if (whenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += WHEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaWhenClauseViolation,
                    "<When> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", whenWords), WHEN_POINTS));

        }

        List<String> thenWords = validationConfig.getCriteria().getThenKeywords() != null ? validationConfig.getCriteria().getThenKeywords() : Collections.emptyList();
        if (thenWords.stream().anyMatch(s -> criteriaLower.contains(s.toLowerCase()))) {
            points += THEN_POINTS;
        } else {
            violations.add(new Violation(ViolationType.CriteriaThenClauseViolation,
                    "<Then> section is not described properly. " +
                            "The criteria should contain any of the following keywords: "
                            + String.join(", ", thenWords), THEN_POINTS));
        }

        if (criteriaLower.length() >= MINIMUM_LENGTH_OF_ACC_CRITERIA) {
            points += LENGTH_POINTS;
        }else{
            violations.add(new Violation(
                    ViolationType.CriteriaLengthViolation,
                    "The criteria should contain a minimum length of " + MINIMUM_LENGTH_OF_ACC_CRITERIA + " characters. " +
                            "It now contains " + criteriaLower.length() + " characters.",
                    LENGTH_POINTS));

        }

        Rating rating = points >= validationConfig.getCriteria().getRatingThreshold() ? Rating.SUCCESS : Rating.FAIL;
        ValidatedAcceptanceCriteria validatedAcceptanceCriteria = ValidatedAcceptanceCriteria
                .builder()
                .item(criteria)
                .violations(violations)
                .rating(rating)
                .build();
        validatedAcceptanceCriteria.setPoints(points, potentialPoints);

        return validatedAcceptanceCriteria;
    }

}
