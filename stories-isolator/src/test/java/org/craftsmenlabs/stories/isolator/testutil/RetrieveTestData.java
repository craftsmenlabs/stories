package org.craftsmenlabs.stories.isolator.testutil;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;

public class RetrieveTestData {
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

    public static final String BACKLOG_WITH_ONE_ISSUE_WITH_ACCEPTANCE_CRITERIA_FIELD  = "{\n" +
            "  \"expand\": \"schema,names\",\n" +
            "  \"startAt\": 0,\n" +
            "  \"maxResults\": 1000,\n" +
            "  \"total\": 154,\n" +
            "  \"issues\": [\n" +
            "    {\n" +
            "      \"expand\": \"operations,editmeta,changelog,transitions,renderedFields\",\n" +
            "      \"id\": \"18511\",\n" +
            "      \"self\": \"http://jira.x.nl/rest/api/2/issue/18511\",\n" +
            "      \"key\": \"DIU-726\",\n" +
            "      \"fields\": {\n" +
            "        \"issuetype\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/issuetype/6\",\n" +
            "          \"id\": \"6\",\n" +
            "          \"description\": \"Created by JIRA Agile - do not edit or delete. Issue type for a user story.\",\n" +
            "          \"iconUrl\": \"http://jira.foo.bar/secure/viewavatar?size=xsmall&avatarId=10915&avatarType=issuetype\",\n" +
            "          \"name\": \"Story\",\n" +
            "          \"subtask\": false,\n" +
            "          \"avatarId\": 10915\n" +
            "        },\n" +
            "        \"components\": [\n" +
            "          {\n" +
            "            \"self\": \"http://jira.foo.bar/rest/api/2/component/11200\",\n" +
            "            \"id\": \"11200\",\n" +
            "            \"name\": \"Profiling\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"timespent\": null,\n" +
            "        \"timeoriginalestimate\": null,\n" +
            "        \"description\": \"As a system administrator I want an auto-complete function that provide me with matching system results so that I can handle large datacenters\\n\\n*scope*\\n* A reusable UI component that can be used in all locations where we want a system administrator to manage datacenters\\n* The component should have an input box, and dropdown box that shows when you type at least 2 characters\\n* The dropdown box shows all the systems for which the description starts with the entered characters (case insensitive)\\n\",\n" +
            "        \"project\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/project/11106\",\n" +
            "          \"id\": \"11106\",\n" +
            "          \"key\": \"EPM\",\n" +
            "          \"name\": \"epic interslicer\",\n" +
            "          \"avatarUrls\": {\n" +
            "            \"48x48\": \"http://jira.foo.bar/secure/projectavatar?pid=11106&avatarId=11829\",\n" +
            "            \"24x24\": \"http://jira.foo.bar/secure/projectavatar?size=small&pid=11106&avatarId=11829\",\n" +
            "            \"16x16\": \"http://jira.foo.bar/secure/projectavatar?size=xsmall&pid=11106&avatarId=11829\",\n" +
            "            \"32x32\": \"http://jira.foo.bar/secure/projectavatar?size=medium&pid=11106&avatarId=11829\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"fixVersions\": [\n" +
            "          \n" +
            "        ],\n" +
            "        \"aggregatetimespent\": null,\n" +
            "        \"resolution\": null,\n" +
            "        \"customfield_10310\": null,\n" +
            "        \"customfield_10302\": \"9223372036854775807\",\n" +
            "        \"customfield_11400\": \"0|zgby24:\",\n" +
            "        \"customfield_10501\": null,\n" +
            "        \"customfield_10502\": \"Given I want to configure a profiling rule involving systems\\r\\nWhen I start typing and stop for 100 ms\\r\\nThen the system provides me with results that match my search string\",\n" +
            "        \"customfield_10306\": null,\n" +
            "        \"aggregatetimeestimate\": null,\n" +
            "        \"customfield_10307\": null,\n" +
            "        \"customfield_10308\": 5.0,\n" +
            "        \"resolutiondate\": null,\n" +
            "        \"customfield_10309\": null,\n" +
            "        \"workratio\": -1,\n" +
            "        \"summary\": \"Search component for systems\",\n" +
            "        \"lastViewed\": null,\n" +
            "        \"watches\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/issue/EPM-632/watchers\",\n" +
            "          \"watchCount\": 1,\n" +
            "          \"isWatching\": false\n" +
            "        },\n" +
            "        \"creator\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/user?username=self\",\n" +
            "          \"name\": \"self\",\n" +
            "          \"key\": \"self\",\n" +
            "          \"emailAddress\": \"self@wcc-group.com\",\n" +
            "          \"avatarUrls\": {\n" +
            "            \"48x48\": \"http://jira.foo.bar/secure/useravatar?ownerId=self&avatarId=11826\",\n" +
            "            \"24x24\": \"http://jira.foo.bar/secure/useravatar?size=small&ownerId=self&avatarId=11826\",\n" +
            "            \"16x16\": \"http://jira.foo.bar/secure/useravatar?size=xsmall&ownerId=self&avatarId=11826\",\n" +
            "            \"32x32\": \"http://jira.foo.bar/secure/useravatar?size=medium&ownerId=self&avatarId=11826\"\n" +
            "          },\n" +
            "          \"displayName\": \"Foo Bar\",\n" +
            "          \"active\": true,\n" +
            "          \"timeZone\": \"Europe/Amsterdam\"\n" +
            "        },\n" +
            "        \"subtasks\": [\n" +
            "          \n" +
            "        ],\n" +
            "        \"created\": \"2016-11-16T11:53:28.000+0100\",\n" +
            "        \"reporter\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/user?username=self\",\n" +
            "          \"name\": \"self\",\n" +
            "          \"key\": \"self\",\n" +
            "          \"emailAddress\": \"self@wcc-group.com\",\n" +
            "          \"avatarUrls\": {\n" +
            "            \"48x48\": \"http://jira.foo.bar/secure/useravatar?ownerId=self&avatarId=11826\",\n" +
            "            \"24x24\": \"http://jira.foo.bar/secure/useravatar?size=small&ownerId=self&avatarId=11826\",\n" +
            "            \"16x16\": \"http://jira.foo.bar/secure/useravatar?size=xsmall&ownerId=self&avatarId=11826\",\n" +
            "            \"32x32\": \"http://jira.foo.bar/secure/useravatar?size=medium&ownerId=self&avatarId=11826\"\n" +
            "          },\n" +
            "          \"displayName\": \"Foo Bar\",\n" +
            "          \"active\": true,\n" +
            "          \"timeZone\": \"Europe/Amsterdam\"\n" +
            "        },\n" +
            "        \"customfield_10000\": null,\n" +
            "        \"aggregateprogress\": {\n" +
            "          \"progress\": 0,\n" +
            "          \"total\": 0\n" +
            "        },\n" +
            "        \"priority\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/priority/3\",\n" +
            "          \"iconUrl\": \"http://jira.foo.bar/images/icons/priorities/major.png\",\n" +
            "          \"name\": \"High\",\n" +
            "          \"id\": \"3\"\n" +
            "        },\n" +
            "        \"customfield_10100\": null,\n" +
            "        \"customfield_10200\": null,\n" +
            "        \"customfield_10201\": null,\n" +
            "        \"customfield_10300\": null,\n" +
            "        \"labels\": [\n" +
            "          \n" +
            "        ],\n" +
            "        \"customfield_10301\": \"EPM-626\",\n" +
            "        \"environment\": null,\n" +
            "        \"timeestimate\": null,\n" +
            "        \"aggregatetimeoriginalestimate\": null,\n" +
            "        \"versions\": [\n" +
            "          \n" +
            "        ],\n" +
            "        \"duedate\": null,\n" +
            "        \"progress\": {\n" +
            "          \"progress\": 0,\n" +
            "          \"total\": 0\n" +
            "        },\n" +
            "        \"issuelinks\": [\n" +
            "          \n" +
            "        ],\n" +
            "        \"votes\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/issue/EPM-632/votes\",\n" +
            "          \"votes\": 0,\n" +
            "          \"hasVoted\": false\n" +
            "        },\n" +
            "        \"assignee\": null,\n" +
            "        \"updated\": \"2016-11-16T17:14:07.000+0100\",\n" +
            "        \"status\": {\n" +
            "          \"self\": \"http://jira.foo.bar/rest/api/2/status/10002\",\n" +
            "          \"description\": \"\",\n" +
            "          \"iconUrl\": \"http://jira.foo.bar/images/icons/statuses/open.png\",\n" +
            "          \"name\": \"To Do\",\n" +
            "          \"id\": \"10002\",\n" +
            "          \"statusCategory\": {\n" +
            "            \"self\": \"http://jira.foo.bar/rest/api/2/statuscategory/2\",\n" +
            "            \"id\": 2,\n" +
            "            \"key\": \"new\",\n" +
            "            \"colorName\": \"blue-gray\",\n" +
            "            \"name\": \"To Do\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }]}";

    public static String getExportedJiraJSONTestResultFromResource() {
        return "{\n" +
                "  \"expand\": \"schema,names\",\n" +
                "  \"startAt\": 0,\n" +
                "  \"maxResults\": 1000,\n" +
                "  \"total\": 464,\n" +
                "  \"issues\": [\n" +
                "    {\n" +
                "      \"expand\": \"operations,editmeta,changelog,transitions,renderedFields\",\n" +
                "      \"id\": \"1721342\",\n" +
                "      \"self\": \"http://jira.foo.bar/rest/api/2/issue/17982\",\n" +
                "      \"key\": \"EPM-512\",\n" +
                "      \"fields\": {\n" +
                "        \"issuetype\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/issuetype/6\",\n" +
                "          \"id\": \"6\",\n" +
                "          \"description\": \"Created by JIRA Agile - do not edit or delete. Issue type for a user story.\",\n" +
                "          \"iconUrl\": \"http://jira.foo.bar/secure/viewavatar?size=xsmall&avatarId=10915&avatarType=issuetype\",\n" +
                "          \"name\": \"Story\",\n" +
                "          \"subtask\": false,\n" +
                "          \"avatarId\": 10915\n" +
                "        },\n" +
                "        \"components\": [],\n" +
                "        \"timespent\": null,\n" +
                "        \"timeoriginalestimate\": null,\n" +
                "        \"description\": \"As a super office user I would like to be informed about the alarms in my user so I can have the most preferred alarm on top.\\n *Acceptance criteria* Given I select an alarm When a profile (or profiles) contain an Then the system displays a page with the alarm.\",\n" +
                "        \"project\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/project/11106\",\n" +
                "          \"id\": \"11106\",\n" +
                "          \"key\": \"EPM\",\n" +
                "          \"name\": \"epic interslicer\",\n" +
                "          \"avatarUrls\": {\n" +
                "            \"48x48\": \"http://jira.foo.bar/secure/projectavatar?pid=11106&avatarId=11829\",\n" +
                "            \"24x24\": \"http://jira.foo.bar/secure/projectavatar?size=small&pid=11106&avatarId=11829\",\n" +
                "            \"16x16\": \"http://jira.foo.bar/secure/projectavatar?size=xsmall&pid=11106&avatarId=11829\",\n" +
                "            \"32x32\": \"http://jira.foo.bar/secure/projectavatar?size=medium&pid=11106&avatarId=11829\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"fixVersions\": [],\n" +
                "        \"aggregatetimespent\": null,\n" +
                "        \"resolution\": null,\n" +
                "        \"customfield_10310\": null,\n" +
                "        \"customfield_10302\": \"9223372036854775807\",\n" +
                "        \"customfield_10401\": \"0|zgbujw:\",\n" +
                "        \"customfield_10306\": null,\n" +
                "        \"aggregatetimeestimate\": null,\n" +
                "        \"customfield_10307\": null,\n" +
                "        \"customfield_10308\": null,\n" +
                "        \"resolutiondate\": null,\n" +
                "        \"customfield_10309\": null,\n" +
                "        \"workratio\": -1,\n" +
                "        \"summary\": \"interslice\",\n" +
                "        \"lastViewed\": \"2013-1-1T15:47:39.369+0200\",\n" +
                "        \"watches\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/issue/EPM-512/watchers\",\n" +
                "          \"watchCount\": 1,\n" +
                "          \"isWatching\": false\n" +
                "        },\n" +
                "        \"creator\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/user?username=foo\",\n" +
                "          \"name\": \"foo\",\n" +
                "          \"key\": \"foo\",\n" +
                "          \"emailAddress\": \"foo@wfoo.bar\",\n" +
                "          \"avatarUrls\": {\n" +
                "            \"48x48\": \"http://jira.foo.bar/secure/useravatar?ownerId=foo&avatarId=11826\",\n" +
                "            \"24x24\": \"http://jira.foo.bar/secure/useravatar?size=small&ownerId=foo&avatarId=11826\",\n" +
                "            \"16x16\": \"http://jira.foo.bar/secure/useravatar?size=xsmall&ownerId=foo&avatarId=11826\",\n" +
                "            \"32x32\": \"http://jira.foo.bar/secure/useravatar?size=medium&ownerId=foo&avatarId=11826\"\n" +
                "          },\n" +
                "          \"displayName\": \"foo\",\n" +
                "          \"active\": true,\n" +
                "          \"timeZone\": \"Europe/Amsterdam\"\n" +
                "        },\n" +
                "        \"subtasks\": [],\n" +
                "        \"created\": \"2013-1-1T10:15:19.000+0200\",\n" +
                "        \"reporter\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/user?username=foo\",\n" +
                "          \"name\": \"foo\",\n" +
                "          \"key\": \"foo\",\n" +
                "          \"emailAddress\": \"foo@foo.bar\",\n" +
                "          \"avatarUrls\": {\n" +
                "            \"48x48\": \"http://jira.foo.bar/secure/useravatar?ownerId=foo&avatarId=11826\",\n" +
                "            \"24x24\": \"http://jira.foo.bar/secure/useravatar?size=small&ownerId=foo&avatarId=11826\",\n" +
                "            \"16x16\": \"http://jira.foo.bar/secure/useravatar?size=xsmall&ownerId=foo&avatarId=11826\",\n" +
                "            \"32x32\": \"http://jira.foo.bar/secure/useravatar?size=medium&ownerId=foo&avatarId=11826\"\n" +
                "          },\n" +
                "          \"displayName\": \"foo\",\n" +
                "          \"active\": true,\n" +
                "          \"timeZone\": \"Europe/Amsterdam\"\n" +
                "        },\n" +
                "        \"customfield_10000\": null,\n" +
                "        \"aggregateprogress\": {\n" +
                "          \"progress\": 0,\n" +
                "          \"total\": 0\n" +
                "        },\n" +
                "        \"priority\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/priority/3\",\n" +
                "          \"iconUrl\": \"http://jira.foo.bar/images/icons/priorities/major.png\",\n" +
                "          \"name\": \"High\",\n" +
                "          \"id\": \"3\"\n" +
                "        },\n" +
                "        \"customfield_10100\": null,\n" +
                "        \"customfield_10200\": null,\n" +
                "        \"customfield_10201\": null,\n" +
                "        \"customfield_10300\": null,\n" +
                "        \"labels\": [],\n" +
                "        \"customfield_10301\": null,\n" +
                "        \"environment\": null,\n" +
                "        \"timeestimate\": null,\n" +
                "        \"aggregatetimeoriginalestimate\": null,\n" +
                "        \"versions\": [],\n" +
                "        \"duedate\": null,\n" +
                "        \"progress\": {\n" +
                "          \"progress\": 0,\n" +
                "          \"total\": 0\n" +
                "        },\n" +
                "        \"issuelinks\": [],\n" +
                "        \"votes\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/issue/EPM-512/votes\",\n" +
                "          \"votes\": 0,\n" +
                "          \"hasVoted\": false\n" +
                "        },\n" +
                "        \"assignee\": null,\n" +
                "        \"updated\": \"2013-1-1T11:11:59.000+0200\",\n" +
                "        \"status\": {\n" +
                "          \"self\": \"http://jira.foo.bar/rest/api/2/status/10002\",\n" +
                "          \"description\": \"\",\n" +
                "          \"iconUrl\": \"http://jira.foo.bar/images/icons/statuses/open.png\",\n" +
                "          \"name\": \"To Do\",\n" +
                "          \"id\": \"10002\",\n" +
                "          \"statusCategory\": {\n" +
                "            \"self\": \"http://jira.foo.bar/rest/api/2/statuscategory/2\",\n" +
                "            \"id\": 2,\n" +
                "            \"key\": \"new\",\n" +
                "            \"colorName\": \"blue-gray\",\n" +
                "            \"name\": \"To Do\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
    }

    public static String getExportedTrelloJSONTestResultFromResource() {
        return "[ \n" +
                "{ \n" +
                "\"id\": \"581b199ba7dfd7e8f737262c\", \n" +
                "\"checkItemStates\": null, \n" +
                "\"closed\": false, \n" +
                "\"dateLastActivity\": \"2016-11-03T13:47:39.437Z\", \n" +
                "\"desc\": \"As a productowner I want to have a good story, so that my development team has a good start. Given I am productowner When I make a good story Then the team will be happy.\", \n" +
                "\"descData\": { \n" +
                "\"emoji\": {} \n" +
                "}, \n" +
                "\"idBoard\": \"541bdf323234e5fde4d4c847\", \n" +
                "\"idList\": \"541bdf323234e5fde4d4c848\", \n" +
                "\"idMembersVoted\": [], \n" +
                "\"idShort\": 52, \n" +
                "\"idAttachmentCover\": null, \n" +
                "\"manualCoverAttachment\": false, \n" +
                "\"idLabels\": [], \n" +
                "\"name\": \"This should succeed\", \n" +
                "\"pos\": 65535, \n" +
                "\"shortLink\": \"TjrGg23O\", \n" +
                "\"badges\": { \n" +
                "\"votes\": 0, \n" +
                "\"viewingMemberVoted\": false, \n" +
                "\"subscribed\": false, \n" +
                "\"fogbugz\": \"\", \n" +
                "\"checkItems\": 0, \n" +
                "\"checkItemsChecked\": 0, \n" +
                "\"comments\": 0, \n" +
                "\"attachments\": 0, \n" +
                "\"description\": true, \n" +
                "\"due\": null \n" +
                "}, \n" +
                "\"due\": null, \n" +
                "\"idChecklists\": [], \n" +
                "\"idMembers\": [], \n" +
                "\"labels\": [], \n" +
                "\"shortUrl\": \"https://trello.com/c/TjrGg23O\", \n" +
                "\"subscribed\": false, \n" +
                "\"url\": \"https://trello.com/c/TjrGg23O/52-this-should-succeed\" \n" +
                "}" +
                "]";
    }

    public static Issue getJiraTestIssueFromResource() {
        return Issue.builder()
                .summary("interslice")
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
                .issueType("Story")
                .build();
    }

    public static Issue getTrelloTestIssueFromResource() {
        return Issue.builder()
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
                .estimation(0f)
                .build();
    }
}
