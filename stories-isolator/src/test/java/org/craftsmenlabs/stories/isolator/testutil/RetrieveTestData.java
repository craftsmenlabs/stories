package org.craftsmenlabs.stories.isolator.testutil;

import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.model.jira.JiraCSVIssueDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetrieveTestData
{

	public static List<JiraCSVIssueDTO> getTestDataFromResource(String resourceName)
	{
		List<JiraCSVIssueDTO> testItems = new ArrayList<>();
		testItems.add(
				JiraCSVIssueDTO.builder().description(
				""
			+ "As a super office user \n"
			+ "I would like to be informed about the alarms in my user \n"
			+ "so I can have the most preferred alarm on top. \n"
			+ "\n"
			+ "*Acceptance criteria* \n"
			+ "Given I select an alarm \n"
			+ "When a profile (or profiles) contain an \n"
			+ "Then the system displays a page with the alarm. \n"
			+ "\n"
			+ "*Scope* \n"
			+ "* Add weight (eg 0) when profile settings is not used in a alarm "
		).build());

		testItems.add(
			JiraCSVIssueDTO.builder().description(
			   "As a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
			+ "\n"
			+ "*Acceptance criteria* \n"
			+ "Given I am on the backend page\n"
			+ "When I select gather statistics\n"
			+ "Then the system displays a pages with the alarms.\n"
			+ "\n"
			+ "*Scope* \n"
			+ "* Use rest services for everything"
		).build());
		return testItems;
	}

	public static List<Issue> getTestResultFromResource() {
		List<Issue> testItems = new ArrayList<>();

		Issue issue = Issue.builder().
				userstory(
				"As a super office user \n"
				+ "I would like to be informed about the alarms in my user \n"
				+ "so I can have the most preferred alarm on top."
				)
		.acceptanceCriteria(
				  "*Acceptance criteria* \n"
				+ "Given I select an alarm \n"
				+ "When a profile (or profiles) contain an \n"
				+ "Then the system displays a page with the alarm."
		).build();

		testItems.add(issue);
		return testItems;
	}


	public static String getExportedTestDataFromResource(){
		return "Project;Key;Summary;JiraJsonIssue Type;Status;Priority;Resolution;Assignee;Reporter;Creator;Created;Last Viewed;Updated;Resolved;Affects Version/s;Fix Version/s;Component/s;Due Date;Votes;Watchers;Images;Original Estimate;Remaining Estimate;Time Spent;Work Ratio;Sub-Tasks;Linked Issues;Environment;Description;Security Level;Progress;_ Progress;_ Time Spent;_ Remaining Estimate;_ Original Estimate;Labels;Release Notes;Fixed in build;Tested version;Severity;Sprint;Epic Link;Rank;Rank (Obsolete);Flagged;Epic/Theme;Story Points;Business Value\n" +
				"1;Gareth-2001;Implement authentication service;Story;Resolved;High;Fixed;TestUser;TestUser;TestUser;11/10/16 12:46;28/09/16 13:19;07/10/16 11:44;07/10/16 11:44;;;Authentication & Authorization;;0;1;;;0;1560;;;;;\"As a super office user\n" +
				"I would like to be informed about the alarms in my user\n" +
				"so I can have the most preferred alarm on top.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I select an alarm\n" +
				"When a profile (or profiles) contain an\n" +
				"Then the system displays a page with the alarm.\n" +
				"\n" +
				"*Scope*\n" +
				"* Add weight (eg 0) when profile settings is not used in a alarm\n" +
				"As a marketing manager\n" +
				"I would like to be informed about the total amount of alarms in my userbase\n" +
				"so I can keep track of.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I am on the backend page\n" +
				"When I select gather statistics\n" +
				"Then the system displays a pages with the alarms.\n" +
				"\n" +
				"*Scope*\n" +
				"* Use rest services for everything\"\";;100%;100%;300;0;;;n/a;6.1.0build01;;;Sprint 1;Service;0|zgbujw:;9,22E+18;;;2;\n" +
				"2;Gareth-2001;Implement authentication service;Story;Resolved;High;Fixed;TestUser;TestUser;TestUser;11/10/16 12:46;28/09/16 13:19;07/10/16 11:44;07/10/16 11:44;;;Authentication & Authorization;;0;1;;;0;1560;;;;;\"As a super office user\n" +
				"I would like to be informed about the alarms in my user\n" +
				"so I can have the most preferred alarm on top.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I select an alarm\n" +
				"When a profile (or profiles) contain an\n" +
				"Then the system displays a page with the alarm.\n" +
				"\n" +
				"*Scope*\n" +
				"* Add weight (eg 0) when profile settings is not used in a alarm\n" +
				"As a marketing manager\n" +
				"I would like to be informed about the total amount of alarms in my userbase\n" +
				"so I can keep track of.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I am on the backend page\n" +
				"When I select gather statistics\n" +
				"Then the system displays a pages with the alarms.\n" +
				"\n" +
				"*Scope*\n" +
				"* Use rest services for everything\"\";;100%;100%;300;0;;;n/a;6.1.0build01;;;Sprint 1;Service;0|zgbujw:;9,22E+18;;;2;\n" +
				"3;Gareth-2001;Implement authentication service;Story;Resolved;High;Fixed;TestUser;TestUser;TestUser;11/10/16 12:46;28/09/16 13:19;07/10/16 11:44;07/10/16 11:44;;;Authentication & Authorization;;0;1;;;0;1560;;;;;\"As a super office user\n" +
				"I would like to be informed about the alarms in my user\n" +
				"so I can have the most preferred alarm on top.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I select an alarm\n" +
				"When a profile (or profiles) contain an\n" +
				"Then the system displays a page with the alarm.\n" +
				"\n" +
				"*Scope*\n" +
				"* Add weight (eg 0) when profile settings is not used in a alarm\n" +
				"As a marketing manager\n" +
				"I would like to be informed about the total amount of alarms in my userbase\n" +
				"so I can keep track of.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I am on the backend page\n" +
				"When I select gather statistics\n" +
				"Then the system displays a pages with the alarms.\n" +
				"\n" +
				"*Scope*\n" +
				"* Use rest services for everything\"\";;100%;100%;300;0;;;n/a;6.1.0build01;;;Sprint 1;Service;0|zgbujw:;9,22E+18;;;2;\n" +
				"4;Gareth-2001;Implement authentication service;Story;Resolved;High;Fixed;TestUser;TestUser;TestUser;11/10/16 12:46;28/09/16 13:19;07/10/16 11:44;07/10/16 11:44;;;Authentication & Authorization;;0;1;;;0;1560;;;;;\"As a super office user\n" +
				"I would like to be informed about the alarms in my user\n" +
				"so I can have the most preferred alarm on top.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I select an alarm\n" +
				"When a profile (or profiles) contain an\n" +
				"Then the system displays a page with the alarm.\n" +
				"\n" +
				"*Scope*\n" +
				"* Add weight (eg 0) when profile settings is not used in a alarm\n" +
				"As a marketing manager\n" +
				"I would like to be informed about the total amount of alarms in my userbase\n" +
				"so I can keep track of.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I am on the backend page\n" +
				"When I select gather statistics\n" +
				"Then the system displays a pages with the alarms.\n" +
				"\n" +
				"*Scope*\n" +
				"* Use rest services for everything\"\";;100%;100%;300;0;;;n/a;6.1.0build01;;;Sprint 1;Service;0|zgbujw:;9,22E+18;;;2;\n" +
				"Generated at Tue Oct 11 11:04:10 CEST 2016 by TestUser using JIRA;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";
	}

	public static List<JiraCSVIssueDTO> getExportedCSVTestResultFromResource(){
		JiraCSVIssueDTO jiraCSVIssueDTO = JiraCSVIssueDTO.builder()
				.key("Gareth-2001")
				.description(
				"As a super office user\n" +
				"I would like to be informed about the alarms in my user\n" +
				"so I can have the most preferred alarm on top.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I select an alarm\n" +
				"When a profile (or profiles) contain an\n" +
				"Then the system displays a page with the alarm.\n" +
				"\n" +
				"*Scope*\n" +
				"* Add weight (eg 0) when profile settings is not used in a alarm\n" +
				"As a marketing manager\n" +
				"I would like to be informed about the total amount of alarms in my userbase\n" +
				"so I can keep track of.\n" +
				"\n" +
				"*Acceptance criteria*\n" +
				"Given I am on the backend page\n" +
				"When I select gather statistics\n" +
				"Then the system displays a pages with the alarms.\n" +
				"\n" +
				"*Scope*\n" +
				"* Use rest services for everything")
				.rank("0|zgbujw:")
				.build();

		return Arrays.asList(jiraCSVIssueDTO, jiraCSVIssueDTO, jiraCSVIssueDTO, jiraCSVIssueDTO);
	}

	public static String getExportedJiraJSONTestResultFromResource(){
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
		return 	"[ \n" +
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

	public static Issue getJiraTestIssueFromResource(){
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
				.build();
	}

	public static Issue getTrelloTestIssueFromResource(){
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
