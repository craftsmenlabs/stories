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
    mvn clean install package

## Usage
    java -jar stories-launcher/stories-launcher<version>.jar [OPTIONAL PARAMETERS]


### Parameters for ranking:
* --application.dataformat = jirajson
* --application.inputfile = <PATH+FILENAME TO JSON FILE>
* --application.outputfile = <output filepath+name storynator.json>
* --application.url = <http://jira.demo.com host without the jira api extension>
* --application.authkey = <base64 encoded username:password for Jira>
* --application.projectkey = <projectkey used in Jira>
* --application.status = <status for backlogitems used in Jira>

### Parameters for ranking:
* -- ranking.desiredMiniumStableRanking = 70
* -- ranking.desiredMinimumUnstableRanking = 60
* -- ranking.desiredRankingStrategy = Curved

### Parameters for rating
* -- validation.backlog.ratingtreshold = 70
* -- validation.issue.ratingtreshold = 70
* -- validation.story.ratingtreshold = 70
* -- validation.criteria.ratingtreshold = 70
* -- validation.estimation.ratingtreshold = 70


### Constraints
At the moment there are some constraints on the data. 
In order to function properly:
* User stories should end with a dot (.), and
* User stories should be in a format: As a...I...So i
* Acceptance criteria should end with a dot (.)
* User stories should use Gherkin language: Given..When..Then

