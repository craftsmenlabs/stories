package org.craftsmenlabs.stories;

public class StoryValidatorTest
{

//	@Injectable
//	BinaryRanking ranking;
//	@Tested
//	StoryValidator storyValidator;
//
//	TestDataGenerator testDataGenerator = new TestDataGenerator();
//
//	@Test
//	public void convertToValidatorEntriesAreAllConvertedTest() throws Exception
//	{
//		List<IssueValidatorEntry> validatorEntries = storyValidator.convertToValidatorEntries(testDataGenerator.getIssues());
//		assertThat(validatorEntries.size()).isEqualTo(3);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void convertToValidatorEntriesHandlesNullt() throws Exception
//	{
//		storyValidator.convertToValidatorEntries(null);
//	}
//
//	@Test
//	public void testScoringForStories() throws Exception
//	{
//		List<IssueValidatorEntry> entries = testDataGenerator.getMixedValidatorItems(3);
//		storyValidator.scoreStories(entries);
//
//		assertThat(entries.get(0).getViolations().size()).isEqualTo(0);
//		assertThat(entries.get(0).getPointsValuation()).isEqualTo(1.0);
//		assertThat(entries.get(1).getViolations().size()).isEqualTo(1);
//		assertThat(entries.get(1).getPointsValuation()).isEqualTo(0.6f);
//		assertThat(entries.get(2).getViolations().size()).isEqualTo(4);
//		assertThat(entries.get(2).getPointsValuation()).isEqualTo(0.2f);
//	}
//
//	@Test()
//	public void rankStories(@Mocked List<IssueValidatorEntry> entries) throws Exception
//	{
//		new Expectations()
//		{{
//			entries.size();
//			result = 10;
//		}};
//		assertThat(storyValidator.rankStories(entries)).isEqualTo(0.0);
//
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void rankStoriesReturnsExceptionOnNull(@Injectable LinearRanking ranking) throws Exception
//	{
//		storyValidator.rankStories(null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void rankStoriesReturnsExceptionOnEmpty(@Injectable LinearRanking ranking) throws Exception
//	{
//		storyValidator.rankStories(Collections.emptyList());
//	}
//
//	@Test
//	public void testRateRanking() throws Exception
//	{
//		Rating rating = storyValidator.rateRanking(80);
//		assertThat(rating).isEqualTo(Rating.SUCCESS);
//	}
}
