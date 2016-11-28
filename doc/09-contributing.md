# Contributing

We value all input on the project, and will welcome any contributions to the repository. 
For this we do have some guidelines, as we will describe in this document. 

We have two versions; One for non-members of the projects (collaborators on github) and others (the other people on github).
A quick summary:

* We use git-flow for the project.
* Releases are done using maven-release.
* A two-digit release number is used (e.g. 1.3).

## For Non-collaborators
First off, thank you for your contribution, it is well appreciated. We would appreciate it even more if you 
do so in a manner that makes it easy for us to integrate it. 

To contribute, we would like you to use the following steps:

* Fork the repository.
* Create a new branch, based on the latest development branch, with prefix feature/\<name\>*.
* Commit your changes to this branch.
* Test the application (mvn clean test).
* Create a pull request.

\* Insert a canonical name of your feature here. E.g: feature/better_language_parsing

A ticket on our waffle.io board will be created automatically and we will process it as soon as possible. 


## For collaborators
When committing to the repository, you should adhere to the following guidelines.

### Version Control
* Do not commit directly to the master or development branch
* Instead, commit to a branch named feature/\<your_feature_here\>.
* When the feature is complete, you should merge it to development.
* Right before a release, we merge development to the master branch and base the release off that.

### Releasing
To perform a release, follow the following steps:
* Merge the development branch to master
* Execute ```mvn release:prepare -Dusername=<username> -Dpassword=<password>```
* Answer the questions regarding the new version number, and new development version number
* Execute ```mvn release:perform -Dusername=<username> -Dpassword=<password>```

If all goes right, you will have created a new release now. 