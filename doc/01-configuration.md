# Configuration
Configuration of the application can be done in all the ways described in the Spring Boot configuration documentation. 
You can do the following things:

* Supply an application.yml in the same folder as the jar
* Or use an application.properties file
* Use command-line arguments
* Use a JSON string in an environment variable

Of all the options available, the first two are probably the best way to go if possible. Of course, you are free to try out any of the other alternatives described in the Spring configuration

## Sample
We supply an internal config files with sane defaults in the classpath of the application, which is shown below. 
You can override any of those properties with your own config file or parameters.
To get the application up and running, you should at least supply the source section of the configuration, which is non-optional. 

```yaml
## Source is the section where you declare your input API.
source:
    type: jira                                             	  # The type source of your application. Currently jira or trello

    jira:
        url: http://jira-url-here.net                         # URL to your JIRA environment
        project-key: GAR                                      # Your project key
        auth-key: KEY                                         # Basic auth key. Base64 encoded string of {username}:{password}
    trello:
        url:                                                  # URL to your Trello environment
        token:
        authKey:
        project-key:
	github:
		url:                                              	  # URL to the GitHub API
		project-key:                                      	  # Name of your project repository
		auth-key:                                         	  # GitHub username of the owner of the repository
		token:                                            	  # Personal access token of the owner


## The ouput data needs to be sent somewhere. There are multiple options available.
report:
  dashboard:
    type: false
    url: http://localhost:8090
    token:
  file:
    type: false
    location: /path/to/file

## Filters define what information we should use to filter the issues.
filters:
    status: "To Do"

## JIRA Agile uses custom fields to store some information it needs.
## To use the Storynator to it's full potential, you should create additional fields that represent the different data of the issue.
field-mapping:
    rank: "customfield_11400"
                                  # The rank field present in all issue types. Used for the curved ranking
    feature:
        estimation: "customfield_11402"
        acceptance-criteria: "customfield_11403"
    bug:
        reproduction-path: "customfield_13001"
        software: "customfield_13002"
        expected-behavior: "customfield_13003"
        acceptation-criteria: "customfield_13004"
    epic:
        goal: "customfield_13005"

## Defines what parts of the validation are active. General ones are:
##
## rating-threshold: The amount of points needed for a SUCCESS rating. 0-100 for backlog and 0-1 for everything else
## fill-fields: Determine score based on those fields filled.
## active: Whether you want to enable this or not
##
##
validation:
    backlog:
        rating-threshold: 60.0
    feature:
        active: true
        rating-threshold: 0.7
    story:
        rating-threshold: 0.6
        active: true

        # Keywords in your user story. Normal format is: As a.... I want... So that..
        as-keywords: ["as a"]
        i-keywords: ["i want"]
        so-keywords: ["so"]
    bug:
        active: true
        fill-fields: ["priority", "reproduction", "software", "expected", "acceptation"]
        rating-threshold: 0.7
    epic:
        active: true
        fill-fields: ["goal"]
    criteria:
        rating-threshold: 0.7
        active: false

        # Keywords in your criteria. Normal format is: Given.. When... Then
        given-keywords: ["given"]
        when-keywords: ["when"]
        then-keywords: ["then"]
    estimation:
        rating-threshold: 0.7
        active: false

```

## Reference
If you want to use the command-line arguments for various reasons, here is the full reference of all arguments. Note that those are exactly the same as when you would provide an application.properties file!

| Parameter | Default | Description | Valid Values |
|-----------|---------|-------------|--------------|
| source.type| jira | The source API type | trello / jira |
| source.jira.url | - | The URL of your JIRA installation | Any URL
| source.jira.project-key | - | The key of the project which you want to validate | Any String |
| source.jira.auth-key | - | Auth key, base64 encoded string containing _username:password_ | Any String |
| source.trello.url | - | Trello url | Any URL |
| source.trello.token | - | Trello API token| Any String|
| source.trello.auth-key | - | Trello auth key | Any string |
| source.trello.project-key | - | Trello project key | any string|
| source.github.url | - | GitHub url | any URL|
| source.github.auth-key | - | GitHub auth key | any string|
| source.github.project-key | - | GitHub project key | any string|
| source.github.token | - | GitHub token | any string|
| report.dashboard.enabled | false | Report result to dashboard. Enterprise feature. | true / false |
| report.dashboard.url | - | Dashboard internet location | Any URL |
| report.dashboard.token | - | Dashboard token | Any String |
| report.file.enabled | false | Report result to file | true / false |
| report.file.location | /path/to/file | File to write result to. Must be a file, not a directory | Any filepath |
| filters.status | To Do | Only retrieve issues/cards with this status | Any String |
| field-mapping.rank | - | Custom field the rank of your issue is in | Any String |
| field-mapping.feature.estimation | - | Custom field the estimation of your user stories are in | Any String |
| field-mapping.feature.acceptance-criteria | - | Custom field the estimation of your user stories are in | Any String |
| field-mapping.bug.reproduction-path | - | Custom field the reproduction path of your bugs are in | Any String |
| field-mapping.bug.software | - | Custom field the software of your bugs are in | Any String |
| field-mapping.bug.expected-behavior | - | Custom field the expected behavior of your bugs are in | Any String |
| field-mapping.bug.acceptance-criteria | - | Custom field the acceptance criteria of your bugs are in | Any String |
| field-mapping.epic.goal | - | Custom field the goal of your epics are in | Any String
| validation.backlog.rating-threshold | 60.0 | Minimal backlog score for a success rating | 0.0 - 100.0 (float) |
| validation.feature.active | true | Whether to enable the validation of features (User Stories) | true / false |
| validation.feature.rating-threshold | 0.7 | Minimal scores for a feature entry to be rated success | 0-1 (float) |
| validation.criteria.active | false | Whether to enable the validation of acceptance criteria in your features (User Stories) | true / false |
| validation.criteria.rating-threshold | 0.7 | Minimal scores for the acceptance criteria to be rated success | 0-1 (float) |
| validation.criteria.given-keywords | ["given"] | Keywords used in your acceptance criteria for the given part | Array of Strings |
| validation.criteria.when-keywords | ["when"] | Keywords used in your acceptance criteria for the when part | Array of Strings |
| validation.criteria.then-keywords | ["then"] | Keywords used in your acceptance criteria for the then part | Array of Strings |
| validation.estimation.active | false | Whether to enable the validation of estimation in your features (User Stories) | true / false |
| validation.estimation.rating-threshold | 0.7 | Minimal scores for an estimation to be rated success | 0-1 (float) |
| validation.story.active | true | Whether to enable the validation of stories in your features (User Stories) | true / false |
| validation.story.rating-threshold | 0.7 | Minimal score for a story to be rated success | 0-1 (float) |
| validation.story.as-keywords | ["as a"] | Keywords for the 'As a' part of your user stories | Array of Strings |
| validation.story.i-keywords | ["i want"] | Keywords for the 'I want' part of your user stories | Array of Strings |
| validation.story.so-keywords | ["so"] | Keywords for the 'So that' part of your user stories | Array of Strings |
| validation.bug.active | true | Whether to enable validation of bugs in your backlog | true / false |
| validation.bug.fill-fields | [See sample] | What fields to be filled for a maximum score on bugs | Array of strings |
| validation.bug.rating-threshold | 0.7 | Minimal score for a bug to be marked success | 0-1 (float) |
| validation.epic.active | true | Whether to enable validation of epics in your backlog | true / false |
| validation.epic.fill-fields | [See sample] | What fields to be filled for a maximum score on epics | Array of strings |
| validation.epic.rating-threshold | 0.7 | Minimal score for an epic to be marked success | 0-1 (float) |


