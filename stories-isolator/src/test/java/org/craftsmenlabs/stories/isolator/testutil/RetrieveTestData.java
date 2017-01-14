package org.craftsmenlabs.stories.isolator.testutil;

import org.craftsmenlabs.stories.api.models.items.base.Feature;

public class RetrieveTestData {
    public static Feature getJiraTestIssueFromResource() {
        return Feature.builder()
                .summary("interslice")
                .externalURI("http://jira.foo.bar/projects/EPM/issues/EPM-512")
                .userstory(
                        "As a super office user " +
                                "I would like to be informed about the alarms in my user " +
                                "so I can have the most preferred alarm on top."
                )
                .acceptanceCriteria(
                        "*Acceptance criteria* " +
                                "Given I select an alarm " +
                                "When a profile (or profiles) contain an " +
                                "Then the system displays a page with the alarm."
                )
                .rank("0|zgbujw:")
                .key("EPM-512")
                .estimation(0f)
                .build();
    }

    public static Feature getTrelloTestIssueFromResource() {
        return Feature.builder()
                .userstory(
                        "As a productowner " +
                                "I want to have a good story, " +
                                "so that my development team has a good start."
                )
                .acceptanceCriteria(
                        "Given I am productowner " +
                                "When I make a good story " +
                                "Then the team will be happy."
                )
                .rank("0")
                .key("581b199ba7dfd7e8f737262c")
                .externalURI("https://trello.com/c/TjrGg23O/52-this-should-succeed")
                .estimation(0f)
                .build();
    }
}
