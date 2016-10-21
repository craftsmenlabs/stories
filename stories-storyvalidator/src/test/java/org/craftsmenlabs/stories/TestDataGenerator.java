package org.craftsmenlabs.stories;

import java.util.*;
import java.util.stream.Collectors;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.craftsmenlabs.stories.api.models.validatorentry.IssueValidatorEntry;

/**
 *
 */
public class TestDataGenerator
{
	private List<String> stories =
		Arrays.asList("As a tester \nI want to \nso i can do it", "As a developer \nI want", "As a developer");
	private List<String> accCrits = Arrays
		.asList("Given ther \n Whennt to \n Thencan do it", "Given ther \n Whennt to \n Thencan do it", "Given ther \n do it");

	public List<Issue> getIssues()
	{
		List<Issue> testData = new ArrayList<>();
		for (int i = 0; i < stories.size(); i++)
		{
			testData.add(Issue.builder()
					.userstory(stories.get(i))
					.acceptanceCriteria(accCrits.get(i))
                    .build());
		}
		return testData;
	}

	public List<Issue> getGoodIssues(int amount)
	{
		List<Issue> testData = new ArrayList<>();
        List<Issue> issues = getIssues();

		for (int i = 0; i < amount; i++)
		{
			testData.add(issues.get(0));
		}
		return testData;
	}

	public List<Issue> getMixedValidatorItems(int amount)
	{
		List<Issue> testData = new ArrayList<>();
        List<Issue> issues = getIssues();

        for (int i = 0; i < amount; i++)
		{
			testData.add(issues.get(i % 3));
		}
		return testData;
	}

	public List<Issue> getBadValidatorItems(int amount)
	{
		List<Issue> testData = new ArrayList<>();
		List<Issue> issues = getIssues();

		for (int i = 0; i < amount; i++)
		{
			testData.add(Issue.builder().userstory("TEST").acceptanceCriteria("TEST").build());
		}
		return testData;
	}

    public BacklogValidatorEntry getGoodBacklog(int amount){
        return BacklogValidatorEntry.builder().issueValidatorEntries(
            getGoodIssues(amount).stream()
                    .map(issue ->
                            IssueValidatorEntry.builder()
                                    .issue(issue)
                                    .pointsValuation(0f)
                                    .violations(new ArrayList<>())
                                    .build())
                    .collect(Collectors.toList())
        ).build();
    }

    public BacklogValidatorEntry getMixedBacklog(int amount){
        return BacklogValidatorEntry.builder().issueValidatorEntries(
                getMixedValidatorItems(amount).stream()
                        .map(issue ->
                                IssueValidatorEntry.builder()
                                        .issue(issue)
                                        .pointsValuation(0f)
                                        .violations(new ArrayList<>())
                                        .build())
                        .collect(Collectors.toList())
        ).build();
    }

	public static Backlog getBacklog(List<Issue> issues)
	{
		Backlog b = new Backlog();
		b.setIssues(issues);
		return b;
	}
}
