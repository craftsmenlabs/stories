package org.craftsmenlabs.stories;

import org.craftsmenlabs.stories.api.models.items.Feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TestDataGenerator
{
	private List<String> stories =
		Arrays.asList("As a tester \nI want to \nso i can do it", "As a developer \nI want", "As a developer");
	private List<String> accCrits = Arrays
		.asList("Given ther \n Whennt to \n Thencan do it", "Given ther \n Whennt to \n Thencan do it", "Given ther \n do it");

	public List<Feature> getIssues()
	{
		List<Feature> testData = new ArrayList<>();
		for (int i = 0; i < stories.size(); i++)
		{
			testData.add(Feature.builder()
					.userstory(stories.get(i))
					.acceptanceCriteria(accCrits.get(i))
                    .build());
		}
		return testData;
	}

	public List<Feature> getGoodIssues(int amount)
	{
		List<Feature> testData = new ArrayList<>();
        List<Feature> features = getIssues();

		for (int i = 0; i < amount; i++)
		{
			testData.add(features.get(0));
		}
		return testData;
	}

	public List<Feature> getMixedValidatorItems(int amount)
	{
		List<Feature> testData = new ArrayList<>();
        List<Feature> features = getIssues();

        for (int i = 0; i < amount; i++)
		{
			testData.add(features.get(i % 3));
		}
		return testData;
	}

	public List<Feature> getBadValidatorItems(int amount)
	{
		List<Feature> testData = new ArrayList<>();
		List<Feature> features = getIssues();

		for (int i = 0; i < amount; i++)
		{
			testData.add(Feature.builder().userstory("TEST").acceptanceCriteria("TEST").build());
		}
		return testData;
	}
//
//    public BacklogValidatorEntry getGoodBacklog(int amount){
//        return BacklogValidatorEntry.builder().featureValidatorEntries(
//            getGoodIssues(amount).stream()
//                    .map(issue ->
//                            ValidatedFeature.builder()
//                                    .feature(issue)
//                                    .pointsValuation(0f)
//                                    .violations(new ArrayList<>())
//                                    .build())
//                    .collect(Collectors.toList())
//        ).build();
//    }
//
//    public BacklogValidatorEntry getMixedBacklog(int amount){
//        return BacklogValidatorEntry.builder().featureValidatorEntries(
//                getMixedValidatorItems(amount).stream()
//                        .map(issue ->
//                                ValidatedFeature.builder()
//                                        .feature(issue)
//                                        .pointsValuation(0f)
//                                        .violations(new ArrayList<>())
//                                        .build())
//                        .collect(Collectors.toList())
//        ).build();
//    }
//
//	public static Backlog getBacklog(List<Feature> features)
//	{
//		Backlog b = new Backlog();
//		b.setFeatures(features);
//		return b;
//	}
}
