# GOED DIGITAL Starter Profiles Constants
=======================================
---
This module provides predefined profiled annotations and constants for use across all projects that require to use Goed environement standard profiles.

There are 4 profiles that a Goed Digital Developer will be building applications for:

1. `local` => Local development environment
2. `dev` => Development environment
3. `uat` => User Acceptance Test environment
4. `prod` => Production environment

And a 5th profile `cloud` has been included if one wishes to potentially configure `cloud` configurations to run locally (combined with local profile).

## USING The Profiles.
==================

The 5 profiles annotations made available map direct to the 5 profiles discussed above:

1. `@LOCAL` => local
2. `@DEVELOPMENT` => dev
3. `@UAT` => uat
4. `@PRODUCTION` => prod
5. `@CLOUD` => cloud

Therefore by annotating a Spring @BEAN, @CONFIGURATION, @SERVICE, etc.  Will indicate that that component will only be available for applications running the equivalent profile.

For example, we have the following `@Configuration`.

```
@UAT
@DEVELOPMENT
@Configuration
public class ExampleConfig {

......
......
.....

}

```

This configuration will ONLY be instanciated and available with any application running either the UAT or DEVELOPMENT profiles (eg: spring.profiles.active=uat).

