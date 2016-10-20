[![Stories in Ready](https://badge.waffle.io/craftsmenlabs/stories.png?label=ready&title=Ready)](http://waffle.io/craftsmenlabs/stories)
[![Build Status](https://travis-ci.org/craftsmenlabs/stories.svg?branch=master)](https://travis-ci.org/craftsmenlabs/stories)
[![Coverage Status](https://coveralls.io/repos/github/craftsmenlabs/stories/badge.svg)](https://coveralls.io/github/craftsmenlabs/stories)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f5732d9ff2ce42158989edaffd298688)](https://www.codacy.com/app/ntalens/stories?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=craftsmenlabs/stories&amp;utm_campaign=Badge_Grade)



# Stories
Stories is a Jenkins plugin and is a validator for checking the quality of a backlog (checking all user stories). 

We want to apply the same principles we apply to code quality to the input (the product backlog). 
With Stories we are able to determine the "coverage" of a backlog. 

Things we meassure:
* Is the user story format used? (As a ... I want ... So ...)
* Does the story include acceptance criteria (Gherkin)? (Given ... when ... then ...)
* Is the story estimated?
* What is the age of story (with a threshold of x week)
* When was the story mutated?
* Are all stories linked to an Epic?


## Getting started
### Prerequisities
* [Maven](https://maven.apache.org/) - Dependency Management
* [Jira](https://jira.atlassian.com) - Issue & Project tracking. Currently Stories has been tested on Jira version 6.4 

## Build
    mvn package

## Usage
    java -jar stories-<version>.jar [PARAMETERS]

Parameters
* -f path to the file containing the stories (an export from Jira)
* -df the data format used in the above mentioned file. Choose from: {jirajson, jiracsv}
* -d (optional) The delimiter by which the data is separated
* -url (optional) The url to the Jira api
* -pk (optional) the Jira project key
* -a (optional) the Jira authentication key

### Constraints
At the moment there are some constraints on the data. 
In order to function properly:
* User stories should end with a dot (.), and
* Acceptance criteria should end with a dot (.)

