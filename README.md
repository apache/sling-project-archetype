[![Apache Sling](https://sling.apache.org/res/logos/sling.png)](https://sling.apache.org)

&#32;[![Build Status](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-project-archetype/job/master/badge/icon)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-project-archetype/job/master/)&#32;[![Test Status](https://img.shields.io/jenkins/tests.svg?jobUrl=https://ci-builds.apache.org/job/Sling/job/modules/job/sling-project-archetype/job/master/)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-project-archetype/job/master/test/?width=800&height=600)&#32;[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-project-archetype&metric=alert_status)](https://sonarcloud.io/dashboard?id=apache_sling-project-archetype)&#32;[![JavaDoc](https://www.javadoc.io/badge/org.apache.sling/sling-project-archetype.svg)](https://www.javadoc.io/doc/org.apache.sling/sling-project-archetype)&#32;[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.sling/sling-project-archetype/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.apache.sling%22%20a%3A%22sling-project-archetype%22) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

### Sling Project Template through Maven Archetype

#### Releases

| Version | Description |
|:--------|:------------|
|1.0.0    |Initial Release. Do not use anymore as it has issues with handling embedded bundles|
|1.0.2    |Fixes for the embedded bundles issue and prevents the interactive mode to fail to ask for certain parameters by having no defaults|
|1.0.4    |Updated the Archetype to work with Sling 12|
|1.0.8    |Added a launcher module|
|1.0.12   |Add Java 17 support|
|1.0.14   |Automatically use latest available Sling Starter version, improve launcher for Windows and different Maven setups, include examples in launcher when enabled|

#### Introduction

This archetype will create a Sling project that can be deployed on the Sling.

The **core** and **ui.apps** are empty Maven modules that are ready for you code.

There are also **example** modules with the extension of **.example**. Please copy whatever you
want from these module into the appropriate module. The example modules are not intended to be
installed into Sling.
Both type (regular and example) have the same structure and so you can copy them over without
any problems.

If this archetype was generated with the **optionAll** property set to **y** then an **all** package
will be created that contains both the **core** and the **ui.apps**. This enables the developer
to easily deploy all modules in that project as a single unit (see below).

If this archetype was generated with the **optionAll** property set to **n** then the **core**
bundle will be deployed as embedded bundle of the **ui.apps** package.

#### Why a Separate All Package

For a simple project that only contains one or two modules (bundle and content package) this seems
like overkill and the same can be accomplished using the ui.apps as single deployment package.

That said for a more serious project where there are multiple bundles and content packages this
becomes difficult to handle. Not only needs one Content Package to take the role of the single
deployment package which requires it to add the other modules as dependencies. This can be confusing
and so the dedicated All content package solves that. It contains the dependencies and the logic
how to build and deploy the entire project in one step.

#### Archetype Properties

|Name                 |Description                                                                   |
|:--------------------|:-----------------------------------------------------------------------------|
|groupId              |Maven Group Id|
|artifactId           |Maven Artifact Id|
|version              |Version of your project|
|artifactName         |Project Label used in the Descriptions and Module Name|
|package              |Root Package of the Java Code|
|packageGroup         |Name of the Package Folder where the package is installed in (/etc/packages)|
|appsFolderName       |Folder name under /apps where components etc are installed|
|contentFolderName    |Folder name under /content where the content is added to|
|slingModelSubPackage |Sub Package where Sling Models should be place in with no trailing dots|
|slingHostName        |Host Name or IP Address of the server where Sling is hosted|
|slingPort            |Port to which your Sling instance is bound to|
|optionAll            |Determines if the project is created with an **all** package or packaged inside the ui.apps mdoule (y/n)|
|optionExample        |Determines if the example code is kept in separate modules (s), merged together into single modules (m) or example code is deleted (d)|

#### Usage

You can create a project with this archetype using this command:
```
mvn archetype:generate \
   -DarchetypeGroupId=org.apache.sling \
   -DarchetypeArtifactId=sling-project-archetype \
   -DarchetypeVersion=1.0.14
```
Maven will then guide you through the configuration process to fill in all the
properties and then create the project.

#### Local Build and Usage

* Build this project locally with
```
mvn clean install
```
* Go to your folder where you want your generated project to be
* Generate it with:
```
mvn archetype:generate -DarchetypeCatalog=local
```
* Select this Archetype from a given list
* Provide the requested properties

#### Build and Install Integration Test

There is a simple integration test setup with this archetype.
This script will build both the Merged All and UI (ui.apps packaging) and install them on the
local Sling instance. The resulting pages (from the example code) can be viewed here:

* [All Merged Home](http://localhost:8080/content/testContentAllMer/home.html)
* [UI Merged Home](http://localhost:8080/content/testContentUIMer/home.html)

You can run this all of these test projects using the attached Unix shell script:
```
sh build.run.and.deploy.test.sh
```

The properties for the integration test can be found under
**/test/resources/projects/\[all/notAll\]/archetype.properties**.
