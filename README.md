[![Stories in Ready](https://badge.waffle.io/craftsmenlabs/stories.png?label=ready&title=Ready)](http://waffle.io/craftsmenlabs/stories)
[![Build Status](https://travis-ci.org/craftsmenlabs/stories.svg?branch=master)](https://travis-ci.org/craftsmenlabs/stories)
[![Coverage Status](https://coveralls.io/repos/github/craftsmenlabs/stories/badge.svg)](https://coveralls.io/github/craftsmenlabs/stories)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f5732d9ff2ce42158989edaffd298688)](https://www.codacy.com/app/ntalens/stories?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=craftsmenlabs/stories&amp;utm_campaign=Badge_Grade)



# Stories
Stories is a issue validation framework that continuously validates the quality of a backlog (checking all user stories). 

We want to apply the same principles we apply to code quality to the input (the product backlog). 
With Stories we are able to determine the "coverage" of a product backlog. 

Things we meassure:
* Is the user story format used? (As a ... I want ... So ...)
* Does the story include acceptance criteria (Gherkin)? (Given ... when ... then ...)
* Is the story estimated?
* What is the age of story (with a threshold of x week)
* When was the story mutated?
* Are all stories linked to an Epic?


## Getting started from binaries with your own validation

### Prerequisities
* [Storynator] (https://github.com/craftsmenlabs/stories/releases) - A recent Storynator release
* [Java 8 ](http://www.oracle.com/technetwork/java/javase/overview/index.html) - Java 8
* [Jira](https://jira.atlassian.com) - Issue & Project tracking. Currently Stories has been tested on Jira version 6.4

### Configuration:
Because of the nature of the application, and since no backlog is the same, there is a hefty amount of configuration you can edit. 
The application is provided by sane defaults, except for the API config of your chosen source. 

More about the configuration can be found in the docs section

### Constraints
At the moment there are some constraints on the data. 
In order to function properly:
* User stories should end with a dot (.), and
* User stories should be in a format: As a...I...So i
* Acceptance criteria should end with a dot (.)
* Acceptance criteria should use Gherkin language: Given..When..Then

# Create your own build
### Prerequisities
* [Java 8 ](http://www.oracle.com/technetwork/java/javase/overview/index.html) - Java 8
* [Maven](https://maven.apache.org/) - Dependency Management

## Build
    mvn clean install package -P<community,enterprise>

## Usage
    java -jar stories-launcher/target/stories-launcher<version>.jar [OPTIONAL PARAMETERS]

## Development
	Run profiles for connectivity.
	--spring.profiles.active=<community,enterprise>
