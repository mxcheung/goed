# GOED DIGITAL Spring Boot Starter - FlyWay
======================================
---
This modules intentions is to extend the benefits provided by `Spring Boot` by creating a custom auto
configuration for use to integrate `FlyWay` conveniently into a Spring Boot application.

By default Springs flyway configuration supports the migrate step only, this module provides the added ability to perform a clean
and migrate (with controls to prevent misuse in a production environment).

_See_ [link](https://flywaydb.org) _on information about flyway._
_See_ [link](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html) _on information on creating a custom spring boot auto configuration feature._ 

## Building The Project
=================
---
###Eclipse:
1. Before importing into eclipse execute the `mvn eclipse:eclipse` command to prepare the project to be able to 
be simply imported into the IDE.
2. Within the IDE, select `import existing eclipse project` option and as the eclipse:eclipse command will have
prepared **.project** and **.classpath** files, this will mean minimal setup required.
3. Add the integration-test src and resources to your classpath to ensure your can easily execute integration tests from the IDE.

###Compiling
The project has been designed to be built via `maven` the pom has been configured to be able to execute unit and
integration tests, generate code coverage reports, and code checkstyle testing.

Four Test Profiles have been configured to aid in building the project depending on what you wish to achieve:

1. `unit-tests` will only execute unit tests.
2. `integration-tests` will only execute integration tests.
3. `all-tests` both unit and integration tests are run.
4. `no-tests` no tests run.

The default profile is __all-tests__.

Some example executions are as followed.
* mvn _clean test_ => Creates code coverage report for unit tests.
* mvn _clean verify -P unit-tests_ => Creates code coverage report for unit tests and fails if does not meet coverage ratios.
* mvn _clean verify -P integration-tests_ => Creates code coverage report for unit tests and fails if does not meet coverage ratios. 
* mvn _clean verify -P all-tests_ => Creates code coverage reports for unit and integration tests and fails if does not meet coverage ratios).
* mvn _clean package -P no-tests_ => Does not execute tests and creates the distributable.
* mvn _clean verify -P all-tests checkstyle:check_ => runs all tests, checkstyle and packages.	

Before submitting your code to GIT run __clean verify -P all-tests checkstyle:check__ and build successfully to ensure that all tests and code style checks are completed to ensure Continuous Integration build errors do not arise.
 
##Quick Start
================
---

To add this module within your application, add the following maven dependency to your `pom.xml`

    <dependencies>
  	  <dependency>
      <groupId>au.com.goed</groupId>
	    <artifactId>goed-spring-boot-starter-flyway</artifactId>
	  <version>X.X.X</version>
    </dependency>


Flyway will then be available for use and Spring Boot will auto-configure it for you.  For a reference of the Flyway properties available within Spring Boot,
see the Spring Boot application properties page (search for FLYWAY - to see flyway specific configuration).

_See_ [link](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) _on Spring Boot common application properties._

##Features
==============
---

Enable flyway clean and migrate by adding `flyway-clean-migrate` to your spring.active.profiles configuration.  Blocker exist to prevent this from executing on any application
with the `prod` profile.

This `prod` profile restriction can be overridden with the property `flyway.permit.production.clean.migration` set to true (default false).

