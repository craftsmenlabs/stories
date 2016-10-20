package org.craftsmenlabs.stories.isolator.parser;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.JiraCSVIssueDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class JiraCSVParser implements Parser {

    private static final String[] HEADER = {"Project", "Key", "Summary", "JiraJsonIssue Type", "Status", "Priority", "Resolution", "Assignee", "Reporter", "Creator", "Created", "Last Viewed", "Updated", "Resolved", "Affects Version/s", "Fix Version/s", "Component/s", "Due Date", "Votes", "Watchers", "Images", "Original Estimate", "Remaining Estimate", "Time Spent", "Work Ratio", "Sub-Tasks", "Linked Issues", "Environment", "Description", "Security Level", "Progress", "_ Progress", "_ Time Spent", "_ Remaining Estimate", "_ Original Estimate", "Labels", "Release Notes", "Fixed in build", "Tested version", "Severity", "Sprint", "Epic Link", "Rank", "Rank (Obsolete)", "Flagged", "Epic/Theme", "Story Points", "Business Value"};
    private static final int KEY_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 28;
    private static final int RANK_INDEX = 42;


    public List<Issue> getIssues(String input){
        List<String> items = Arrays.asList(input.split(";"));
        List<JiraCSVIssueDTO> jiraCSVIssues = getJiraCSVIssues(items);


        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        return jiraCSVIssues.stream().map(jiraCSVIssue -> {
            Issue issue = sentenceSplitter.splitSentence(jiraCSVIssue.getDescription());
            issue.setRank(jiraCSVIssue.getRank());

            return issue;
        }).collect(Collectors.toList());
    }

    public static List<JiraCSVIssueDTO> getJiraCSVIssues(List<String> items){
        List<JiraCSVIssueDTO> jiraIssues = new ArrayList<>(items.size()/HEADER.length);

        for (int i = 0; i < items.size(); i+=HEADER.length) {
            String description = removeOuterQuotes(items.get(i + DESCRIPTION_INDEX));
            jiraIssues.add(
                    JiraCSVIssueDTO.builder()
                            .key(items.get(i + KEY_INDEX))
                            .rank(items.get(i + RANK_INDEX))
                            .description(description)
                            .build());

            // the string is only split on ";" , and now it misses the \n after an issue.
            // this compensates for that error.
            i--;
        }

        return jiraIssues;
    }

    private static String removeOuterQuotes(String input){
        if(input.startsWith("\"")){
            input = input.substring(1);
        }
        if(input.endsWith("\"")){
            input = input.substring(0, input.length()-2);
        }

        return input;
    }
}
