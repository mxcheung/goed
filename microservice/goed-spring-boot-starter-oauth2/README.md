# GOED DIGITAL Spring Boot Starter - Oauth2
================================================
---
This modules intentions is to extend the benefits provided by `Spring Boot` by creating a custom auto configuration
for goed Services that require the use Oauth2.

This module has the following benefits:
1.  Automatically enable an appropriately profiles service to become a Resource server.
2.  Enables @PreAuthorize and @PostAuthorize annotations to be used.
3.  Provides a default WebSecurityConfigurerAdapter that locks down all endpoints.
4.  Connects to a remote Authorization token store either via JDBC or the authorization servers token validation endpoint => requiring appropriate profile toggle.
5.  Handles AccessDeniedException that may be thrown via a default controller advice.
6.  Reads the custom goed JWT token from the Authorization header and auto decodes it to a User.class making it available as a \@ModelAttribute.
7.  Provides a Cross Origin Resource Sharing (CORS) once per request filter for handling CORS transactions.

_See_ [link](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html) _on information on creating a custom spring boot auto configuration feature._ 

## Building The Project
================
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
	    <artifactId>goed-spring-boot-starter-oauth2</artifactId>
	  <version>goed-spring-boot-starter-oauth2-X.X.X.jar</version>
    </dependency>
   
Once added any Service profiles (spring.profiles.active) with `prod`, `uat`, `dev` and `cloud` will be configured to be an Outh2 resource server.

The profile must also provide the necessary toggle to decide which token authorization approach (jdbc, endpoint) should be used to authorize the provided Oauth2 token (Authorization header value).

##Configuring Token Verfication Approach
==================================
-
### JDBC Token Authorization
To activate JDBC token authorization add the `jdbcToken` profile to your service (spring.profiles.active=jdbcToken). This requires certain configuration to be provided for your service to connect to the appropriate authorization service:

`goed.oauth2.token.jdbc.url` = the jdbc url for the jdbc token store.
`goed.oauth2.token.jdbc.driver` = the jdbc driver class to access the jdbc token store.
`goed.oauth2.token.jdbc.username` = the jdbc username to access the jdbc store.
`goed.oauth2.token.jdbc.password` = the password for the username provided above.

Yaml representation example:
```
goed:
  oauth2:
    token:
      jdbc:
        url: jdbc:h2:tcp://localhost/~/tokenstore
        driver: org.h2.Driver
        username: username
        password: password
```

### Remote Token Authorization
To activate Remote token authorization (via Authorization server endpoint) add the `remoteToken` profile to your service (spring.profles.active=remoteToken).
This requires certain configuration to be provided for your service to utilize the remote token authorization approach:

`goed.oauth2.token.remote.endpoint` = the authorization server check token endpoint.
`goed.oauth2.token.remote.clientId` = oauth2 client id being used to validate the token.
`goed.oauth2.token.remote.secret` = the oauth2 client secret value.

Yaml representation example:

```
goed:
  oauth2:
    token:
      remote:
        endpoint: http://localhost:9999/lsso/oauth/check_token
        clientId: goedApp
        secret: secret
```
  
##Disabling Oauth2 for Local Environments
==========================================
As profile toggling is enabled by not including any of the following profiles as part of your `spring.profiles.active` will not configure your
service as an Oauth2 resource server:
1.  cloud
2.  prod
3.  uat
4.  dev

Furthermore to ensure no security is used for the profile configuration set the following configuration in your properties:

```
management:
  security:
    enabled: false
   
security:
  basic:
    enabled: false
  ignored: /**
```

##Configuring CORS
=====================
Including this starter as a dependency will automatically provide your application with CORS support.  The following configuration is set by
default (by providing your own configuration the default will be overridden.

```
goed:
  cors:
    allowedCredentials: true
    allowedOrigin: *
    allowedHeader: *
    allowedMethod: *
    maxAge: 3600
    corsPath: *
```

_See_ [link](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/cors/CorsConfiguration.html) _to understand how to configure CORS within Spring.
