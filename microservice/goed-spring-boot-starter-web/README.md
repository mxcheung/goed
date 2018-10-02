# GOED DIGITAL Spring Boot Starter - Web
====================================
---
This modules intentions is to extend the benefits provided by `Spring Boot` by creating a custom auto
configuration for use as the basis of goed's Spring Boot JSON microservices.

The module adds the benefits of standardized exception handling, custom white label error page output, request
logging, and spring cloud sleuth enablement amongst other things (depending on profiles used).

The module is ready and set with the entire Spring MVC stack, embedded Apache Tomcat and the ability to run
the application within a single jar.

The module also enables the Application to be Eureka aware by configuring the application to register itself
with Eureka. A RestTemplate bean qualified with `sdRestTemplate` is also configured to be able to search for Eureka registered services. 

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
	    <artifactId>goed-spring-boot-starter-web</artifactId>
	  <version>goed-spring-boot-starter-web-X.X.X.jar</version>
    </dependency>


And that's all there is to it!  So create a simple boot application like the following and all the goodies
from this project will be ready for you to use.

```java
    @Controller
    @SpringBootApplication
    public class BasicApp {

    @RequestMapping("/")
    @ResponseBody
    public String goedRules() {
      return "goed Rules";
    }

    public static void main(String[] args) {
      SpringApplication.run(BasicApp.class, args);
    }
   }
```

### Exception Handling
---

This module handles a host of exceptions conveniently for you, via the DefaultErrorController.java and the DefaultExceptionControllerAdvice.java classes.  These ensure that all non specific application error handling
is done in a consistent manner that can be used throughout all goed Micro Services.

Default error codes and messages are configured already in `goedMessagesDefaults.properties`.  These can be overriden by adding your own and overrides in a `messages.properties` file within your own project, or configuring your environment properties with `goed.validation.messages.basenames` to indicate which message properties files will be used.


### Request Logging
---

Pre and Post request logging is available via the `RequestLoggingFilter.java` class.  You can configure 4 modes:
1. ALL => logs both pre and post request
2. PRE => logs only pre request
3. POST => logs only the post request
4. NONE => disables logging request

To configure include `request.filter.mode` property in your configuration set to the appropriate level.


###Spring Cloud Sleuth
---

Spring cloud sleuth is configured to provide ease of tracing in logs to be able to identify all a users transactions across multiple services.  By include cloud sleuth on the built path the logs will already include such logging.  If you wish to configure system analysis or elk logging follow spring cloud sleuth documentation for how this can be easily accomplished.

An example configuration 
`spring.zipkin.enabled=true` will enable zipkin log tracing.
`spring.zipkin.baseUrl=http://yourzipkin` defines your zipkin server.

###Eureka Aware
---

Spring Cloud Eureka has been included to allow the application to register itself with a Spring Cloud Eureka
Registration Service.

The following configurations have been pre-set:
`cloud.config.enabled=true`
`eureka.instance.preferIpAddress=true`
`eureka.client.registerWithEureka=true`
`eureka.client.fetchRegistry=true`

The only property that needs to be set is `eureka.client.serviceUrl.defaultZone` which is set to the location of the eureka service(s) (comma seperate to register with multiple).

Note: Eureka is only enabled if run using certain profiles, see => au.com.goed.starter.web.configuration.CloudConfiguration

####Call other Eureka registered services.
---

The project has a configured RestTemplate ready for use to pick up other services registered with the eureka 
registry.  The bean is qualified with `sdRestTemplate` and can be used to find the other services via their
registered name as opposed to url etc.

###Hystrix
---

Hystrix is a library which enables a service to implement circuit-breaker, fallback and bulkhead patterns.
1.  `Circuit breaker`: client resiliency pattern that detects that monitors a remote service call.  If the call is taking too much time the circuit breaker pattern will intercede and kill the call.
2.  `Fallback processing`: when a call to a remote service fails, instead of throwing an exception the service
consumer has the ability to implement an alternative path to prevent a failure from occuring.
3.  `Bulkheads`: breaks the calls to remote resources into their own thread pools thus reducing the risk that
a problem with one slow service call will bring down the entire application.

The `@HystrixCommand` annotation can be used on service methods to implement the above patterns (please see relevant documentation on how to utilise this annotation to implement the appropriate pattern).

Note: Hystrix is only enabled if run using certain profiles, see => au.com.goed.starter.web.configuration.CloudConfiguration