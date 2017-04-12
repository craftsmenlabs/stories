package org.craftsmenlabs.stories.isolator.testutil;

import org.craftsmenlabs.stories.api.models.items.base.Feature;

import java.time.LocalDateTime;

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
                .estimation(null)
                .updatedAt(LocalDateTime.of(2013,1,1,11,11,59))
                .createdAt(LocalDateTime.of(2013,1,1,10,15,19))
                .build();
    }

    public static Feature getTrelloTestIssueFromResource() {
        return Feature.builder()
                .summary("This should succeed")
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
                .estimation(0.0)
                .updatedAt(LocalDateTime.of(2016,11,3,13,47,39,437000000))
                .build();
    }
}
