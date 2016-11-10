package org.craftsmenlabs.stories.isolator;

public class TestData {

    public static final String BACKLOG_WITH_ONE_ISSUE = "{\n" +
            "  \"expand\": \"schema,names\",\n" +
            "  \"startAt\": 0,\n" +
            "  \"maxResults\": 1000,\n" +
            "  \"total\": 154,\n" +
            "  \"issues\": [\n" +
            "    {\n" +
            "      \"expand\": \"operations,versionedRepresentations,editmeta,changelog,transitions,renderedFields\",\n" +
            "      \"id\": \"80972\",\n" +
            "      \"self\": \"https://jira.codecentric.de/rest/api/2/issue/80972\",\n" +
            "      \"key\": \"GAR-154\",\n" +
            "      \"fields\": {\n" +
            "        \"issuetype\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/issuetype/10400\",\n" +
            "          \"id\": \"10400\",\n" +
            "          \"description\": \"\",\n" +
            "          \"iconUrl\": \"https://jira.codecentric.de/download/resources/com.atlassian.jira-core-project-templates:jira-softwaredevelopment-item/story.png\",\n" +
            "          \"name\": \"Story\",\n" +
            "          \"subtask\": false\n" +
            "        },\n" +
            "        \"timespent\": null,\n" +
            "        \"customfield_10150\": null,\n" +
            "        \"project\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/project/13980\",\n" +
            "          \"id\": \"13980\",\n" +
            "          \"key\": \"GAR\",\n" +
            "          \"name\": \"Gareth\",\n" +
            "          \"avatarUrls\": {\n" +
            "            \"48x48\": \"https://jira.codecentric.de/secure/projectavatar?pid=13980&avatarId=14247\",\n" +
            "            \"24x24\": \"https://jira.codecentric.de/secure/projectavatar?size=small&pid=13980&avatarId=14247\",\n" +
            "            \"16x16\": \"https://jira.codecentric.de/secure/projectavatar?size=xsmall&pid=13980&avatarId=14247\",\n" +
            "            \"32x32\": \"https://jira.codecentric.de/secure/projectavatar?size=medium&pid=13980&avatarId=14247\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"fixVersions\": [],\n" +
            "        \"aggregatetimespent\": null,\n" +
            "        \"resolution\": null,\n" +
            "        \"customfield_11400\": \"2|i02txb:\",\n" +
            "        \"customfield_10700\": null,\n" +
            "        \"resolutiondate\": null,\n" +
            "        \"workratio\": -1,\n" +
            "        \"lastViewed\": \"2016-11-08T11:53:06.960+0100\",\n" +
            "        \"watches\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/issue/GAR-154/watchers\",\n" +
            "          \"watchCount\": 1,\n" +
            "          \"isWatching\": true\n" +
            "        },\n" +
            "        \"created\": \"2016-11-08T11:52:02.000+0100\",\n" +
            "        \"customfield_10140\": null,\n" +
            "        \"priority\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/priority/3\",\n" +
            "          \"iconUrl\": \"https://jira.codecentric.de/images/icons/priorities/major.svg\",\n" +
            "          \"name\": \"Major\",\n" +
            "          \"id\": \"3\"\n" +
            "        },\n" +
            "        \"customfield_10300\": null,\n" +
            "        \"labels\": [],\n" +
            "        \"timeestimate\": null,\n" +
            "        \"aggregatetimeoriginalestimate\": null,\n" +
            "        \"versions\": [],\n" +
            "        \"issuelinks\": [],\n" +
            "        \"assignee\": null,\n" +
            "        \"updated\": \"2016-11-08T11:52:02.000+0100\",\n" +
            "        \"status\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/status/10030\",\n" +
            "          \"description\": \"\",\n" +
            "          \"iconUrl\": \"https://jira.codecentric.de/images/icons/subtask.gif\",\n" +
            "          \"name\": \"To Do\",\n" +
            "          \"id\": \"10030\",\n" +
            "          \"statusCategory\": {\n" +
            "            \"self\": \"https://jira.codecentric.de/rest/api/2/statuscategory/2\",\n" +
            "            \"id\": 2,\n" +
            "            \"key\": \"new\",\n" +
            "            \"colorName\": \"blue-gray\",\n" +
            "            \"name\": \"To Do\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"components\": [],\n" +
            "        \"timeoriginalestimate\": null,\n" +
            "        \"customfield_10052\": null,\n" +
            "        \"description\": null,\n" +
            "        \"customfield_10130\": null,\n" +
            "        \"customfield_11100\": null,\n" +
            "        \"customfield_10203\": \"9223372036854775807\",\n" +
            "        \"customfield_12701\": null,\n" +
            "        \"customfield_12502\": null,\n" +
            "        \"customfield_12700\": null,\n" +
            "        \"customfield_10800\": [\n" +
            "          {\n" +
            "            \"self\": \"https://jira.codecentric.de/rest/api/2/user?username=niels.talens\",\n" +
            "            \"name\": \"niels.talens\",\n" +
            "            \"key\": \"niels.talens\",\n" +
            "            \"emailAddress\": \"niels.talens@codecentric.nl\",\n" +
            "            \"avatarUrls\": {\n" +
            "              \"48x48\": \"https://jira.codecentric.de/secure/useravatar?avatarId=10162\",\n" +
            "              \"24x24\": \"https://jira.codecentric.de/secure/useravatar?size=small&avatarId=10162\",\n" +
            "              \"16x16\": \"https://jira.codecentric.de/secure/useravatar?size=xsmall&avatarId=10162\",\n" +
            "              \"32x32\": \"https://jira.codecentric.de/secure/useravatar?size=medium&avatarId=10162\"\n" +
            "            },\n" +
            "            \"displayName\": \"Niels Talens\",\n" +
            "            \"active\": true,\n" +
            "            \"timeZone\": \"Europe/Berlin\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"aggregatetimeestimate\": null,\n" +
            "        \"summary\": \"Extract Storynator DEMO interface to  demo\",\n" +
            "        \"creator\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/user?username=niels.talens\",\n" +
            "          \"name\": \"niels.talens\",\n" +
            "          \"key\": \"niels.talens\",\n" +
            "          \"emailAddress\": \"niels.talens@codecentric.nl\",\n" +
            "          \"avatarUrls\": {\n" +
            "            \"48x48\": \"https://jira.codecentric.de/secure/useravatar?avatarId=10162\",\n" +
            "            \"24x24\": \"https://jira.codecentric.de/secure/useravatar?size=small&avatarId=10162\",\n" +
            "            \"16x16\": \"https://jira.codecentric.de/secure/useravatar?size=xsmall&avatarId=10162\",\n" +
            "            \"32x32\": \"https://jira.codecentric.de/secure/useravatar?size=medium&avatarId=10162\"\n" +
            "          },\n" +
            "          \"displayName\": \"Niels Talens\",\n" +
            "          \"active\": true,\n" +
            "          \"timeZone\": \"Europe/Berlin\"\n" +
            "        },\n" +
            "        \"subtasks\": [],\n" +
            "        \"customfield_10120\": null,\n" +
            "        \"reporter\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/user?username=niels.talens\",\n" +
            "          \"name\": \"niels.talens\",\n" +
            "          \"key\": \"niels.talens\",\n" +
            "          \"emailAddress\": \"niels.talens@codecentric.nl\",\n" +
            "          \"avatarUrls\": {\n" +
            "            \"48x48\": \"https://jira.codecentric.de/secure/useravatar?avatarId=10162\",\n" +
            "            \"24x24\": \"https://jira.codecentric.de/secure/useravatar?size=small&avatarId=10162\",\n" +
            "            \"16x16\": \"https://jira.codecentric.de/secure/useravatar?size=xsmall&avatarId=10162\",\n" +
            "            \"32x32\": \"https://jira.codecentric.de/secure/useravatar?size=medium&avatarId=10162\"\n" +
            "          },\n" +
            "          \"displayName\": \"Niels Talens\",\n" +
            "          \"active\": true,\n" +
            "          \"timeZone\": \"Europe/Berlin\"\n" +
            "        },\n" +
            "        \"customfield_12101\": null,\n" +
            "        \"aggregateprogress\": {\n" +
            "          \"progress\": 0,\n" +
            "          \"total\": 0\n" +
            "        },\n" +
            "        \"customfield_12300\": null,\n" +
            "        \"customfield_12501\": null,\n" +
            "        \"customfield_10202\": \"9223372036854775807\",\n" +
            "        \"customfield_12500\": null,\n" +
            "        \"customfield_11601\": null,\n" +
            "        \"customfield_11600\": null,\n" +
            "        \"environment\": null,\n" +
            "        \"customfield_11801\": null,\n" +
            "        \"customfield_11800\": null,\n" +
            "        \"customfield_11803\": null,\n" +
            "        \"customfield_11802\": null,\n" +
            "        \"duedate\": null,\n" +
            "        \"customfield_11804\": null,\n" +
            "        \"progress\": {\n" +
            "          \"progress\": 0,\n" +
            "          \"total\": 0\n" +
            "        },\n" +
            "        \"votes\": {\n" +
            "          \"self\": \"https://jira.codecentric.de/rest/api/2/issue/GAR-154/votes\",\n" +
            "          \"votes\": 0,\n" +
            "          \"hasVoted\": false\n" +
            "        }\n" +
            "      }\n" +
            "    }]}";
}
