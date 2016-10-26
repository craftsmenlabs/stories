package org.craftsmenlabs.stories.scoring;

import mockit.Expectations;
import mockit.Injectable;
import org.craftsmenlabs.stories.TestDataGenerator;
import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.craftsmenlabs.stories.ranking.CurvedRanking;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class BacklogScorerTest
{
	TestDataGenerator _testDataGenerator = new TestDataGenerator();

	@Test
	public void testPerformScorerAndRatesSucces(@Injectable ScorerConfigCopy validationConfig) throws Exception
	{
		new Expectations()
		{{
			validationConfig.getCriteria().getRatingtreshold();
			result = 0.7f;
		}};

		BacklogValidatorEntry be = BacklogScorer.performScorer(TestDataGenerator.getBacklog(_testDataGenerator.getGoodIssues(3)),
			new CurvedRanking(),
			validationConfig);
		assertThat(be.getRating()).isEqualTo(Rating.SUCCES);
	}

	@Test
	public void testPerformScorerAndRatesFail(@Injectable ScorerConfigCopy validationConfig) throws Exception
	{
		new Expectations()
		{{
			validationConfig.getCriteria().getRatingtreshold();
			result = 0.7f;
		}};

		BacklogValidatorEntry be = BacklogScorer
			.performScorer(TestDataGenerator.getBacklog(_testDataGenerator.getBadValidatorItems(3)),
				new CurvedRanking(),
				validationConfig);
		assertThat(be.getRating()).isEqualTo(Rating.SUCCES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPerformScorer_ReturnsZeroOnEmpty(
		@Injectable BacklogValidatorEntry entry, @Injectable
	ScorerConfigCopy validationConfig) throws Exception
	{
        float score = BacklogScorer.performScorer(null, null, validationConfig).getPointsValuation();
        assertThat(score).isCloseTo(0.0f, withinPercentage(1));
    }
}
