package org.craftsmenlabs.stories.ranking;

public class CurvedRankingTest// implements RankingTest
{
//
//    @Tested
//    CurvedRanking ranking;
//
//    TestDataGenerator testDataGenerator = new TestDataGenerator();
//
//    StoryValidator _storyValidator = new StoryValidator();
//
//    @Override @Test
//    public void testRankingHandlesNullWorks() throws Exception
//    {
//        float rank = ranking.createRanking(null);
//        assertThat(rank).isEqualTo(0.0f);
//    }
//
//    @Override @Test
//    public void testRankingHandlesEmptyWorks() throws Exception
//    {
//        BacklogValidatorEntry backlogValidatorEntryWithEmptyList = BacklogValidatorEntry.builder().issueValidatorEntries(null).build();
//        float rank = ranking.createRanking(backlogValidatorEntryWithEmptyList);
//        assertThat(rank).isEqualTo(0.0f);
//    }
//
//    @Override @Test
//    public void testRankingIsZeroWithOnlyUnscoredItemsWorks() throws Exception
//    {
//        BacklogValidatorEntry goodBacklog = testDataGenerator.getGoodBacklog(10);
//        float rank = ranking.createRanking(goodBacklog);
//        assertThat(rank).isEqualTo(0.0f);
//    }
//
//    @Override @Test
//    public void testRankingIsOneWithPerfectItemsWorks() throws Exception
//    {
//        List<Issue> issues = testDataGenerator.getGoodIssues(20);
//        BacklogValidatorEntry testEntries = _storyValidator.scoreStories(issues);
//        float rank = ranking.createRanking(testEntries);
//        assertThat(rank).isCloseTo(1.0f, withinPercentage(1.0));
//    }
//
//    @Override @Test
//    public void testRankingRankWithMixedSethWorks() throws Exception
//    {
//        BacklogValidatorEntry testEntries = _storyValidator.scoreStories(testDataGenerator.getMixedValidatorItems(20));
//        float rank = ranking.createRanking(testEntries);
//        assertThat(rank).isCloseTo(0.707f, withinPercentage(1));
//    }
//
//    @Override @Test
//    public void testRankingIncreasesOnGoodInputWorks() throws Exception
//    {
//        BacklogValidatorEntry testEntries = _storyValidator.scoreStories(testDataGenerator.getMixedValidatorItems(20));
//        float rank = ranking.createRanking(testEntries);
//        assertThat(rank).isCloseTo(0.707f, withinPercentage(1));
//
//        BacklogValidatorEntry testEntries2 = _storyValidator.scoreStories(testDataGenerator.getGoodIssues(3));
//        float rank2 = ranking.createRanking(testEntries2);
//        assertThat(rank2).isCloseTo(1.0f, withinPercentage(1));
//
//        testEntries2.getIssueValidatorEntries().addAll(testEntries.getIssueValidatorEntries());
//        float rank3 = ranking.createRanking(testEntries2);
//        assertThat(rank3).isCloseTo(0.763f, withinPercentage(1));
//        assertThat(rank).isLessThan(rank3);
//    }
//
//    @Override @Test
//    public void testRankingDecreasesOnBadInputWorks() throws Exception
//    {
//        BacklogValidatorEntry mixedBacklog = _storyValidator.scoreStories(testDataGenerator.getMixedValidatorItems(20));
//        float rank = ranking.createRanking(mixedBacklog);
//        assertThat(rank).isCloseTo(0.707f, withinPercentage(1));
//
//        BacklogValidatorEntry goodBacklog = testDataGenerator.getGoodBacklog(3);
//        float rank2 = ranking.createRanking(goodBacklog);
//        assertThat(rank2).isEqualTo(0.0f);
//
//        goodBacklog.getIssueValidatorEntries().addAll(mixedBacklog.getIssueValidatorEntries());
//        float rank3 = ranking.createRanking(goodBacklog);
//        assertThat(rank3).isCloseTo(0.57401336f, withinPercentage(1));
//        assertThat(rank).isGreaterThan(rank3);
//    }
//
//    @Override @Test
//    public void testRankingDecreasesMinimalOnBadBottomInputWorks() throws Exception
//    {
//        BacklogValidatorEntry mixedBacklog = _storyValidator.scoreStories(testDataGenerator.getMixedValidatorItems(20));
//        float rank = ranking.createRanking(mixedBacklog);
//        assertThat(rank).isCloseTo(0.707f, withinPercentage(1));
//
//        BacklogValidatorEntry unrankedBacklog = testDataGenerator.getGoodBacklog(3);
//        mixedBacklog.getIssueValidatorEntries().addAll(unrankedBacklog.getIssueValidatorEntries());
//
//        float rank3 = ranking.createRanking(mixedBacklog);
//        assertThat(rank3).isCloseTo(0.686f, withinPercentage(1));
//        assertThat(rank).isGreaterThan(rank3);
//    }
//
//    @Test
//    public void testCurve() throws Exception
//    {
//        float curve = ranking.curvedQuotient(10, 20);
//        assertThat(curve).isEqualTo(0.75f);
//    }
}
