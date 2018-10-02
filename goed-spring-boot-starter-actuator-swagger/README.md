#GOED DIGITAL Spring Boot Starter - Actuator and Swagger
========================================================================================
---
This starter module will activate swagger and an accompanying Swagger UI to your project as well
as activating Spring Boot Actuators and Enabling the Hystrix Dashboard actuator.

The module also configures a custom actuator that will list all the current actuators in the system with
their current configuration.

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
	    <artifactId>goed-spring-boot-starter-actuator-swagger</artifactId>
	  <version>goed-spring-boot-starter-actuator-swagger-X.X.X.jar</version>
    </dependency>
    
###Actuator
---
The module configures default actuator settings (an actuator enabler). If you wish to modify them override or code the necessary adjustments.

For local development it might help to set the following configuration property to disable security set `management.security.enabled` to false.

The custom list all actuator is named by default 'listAll' this can be overwritten with the configuration
property `actuator.custom.list.all`.

##Oauth2 accessing the sensitive Actuator Endpoints
---
When configuring your service to use OAUTH2 (resource server) by giving your user the role (authority) of `ACTUATOR` they'll be able to see the sensitive actuator endpoints.

### Hystrix Dashboard
---
The module also enables the Hystrix (Circuit breaker pattern) dashboard monitor.  By hitting /hystrix.stream on the application you'll be able to monitor the application's fault tolerance.
Is only enabled with one of the `dev`, `uat`, `prod`, `cloud` profiles attached to the application.

###Swagger
---
The module configures the default swagger setting and ui (a swagger enabler). if you wish to modify them, then it will be up to your application to configure/code as necessary.

The swagger ui is available on.  /swagger-ui.html

NOTE: Only endpoint in the `au.com.goed` package will be visible within the console.  Hence stick to goed package standard and you'll be alright.

Configuration properties to use to assign various Swagger configurations (recommend most of these are set in your application).
* `api.description` => Description about the API.
* `api.terms.of.service` => Terms of service information pertaining to the API.
* `api.license` => License information pertaining to the API.
* `api.license.url` => License URL