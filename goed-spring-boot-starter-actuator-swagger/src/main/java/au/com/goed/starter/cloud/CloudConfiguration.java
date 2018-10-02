package au.com.goed.starter.cloud;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

import au.com.goed.starter.actuator.ActuatorConfiguration;
import au.com.goed.starter.profile.CLOUD;
import au.com.goed.starter.profile.DEVELOPMENT;
import au.com.goed.starter.profile.PRODUCTION;
import au.com.goed.starter.profile.UAT;

/**
 * <p>
 * Configuration class for enabling Eureka and Hystrix dashboard on profile that is active. Note that dev,
 * uat, and prod environments will automatically have cloud enablement (service discovery and hystrix), for
 * other environments by including the 'cloud' profile then cloud enablement will activate.
 * </p>
 * <p>
 * For example by setting: <i>spring.profiles.active=local,cloud</i> you'll enable the local environment to
 * be cloud enabled.
 * </p>
 * 
 * @author Goed Bezig
 */
@PRODUCTION
@DEVELOPMENT
@UAT
@CLOUD
@Configuration
@EnableHystrixDashboard
@AutoConfigureBefore(ActuatorConfiguration.class)
class CloudConfiguration {
    
}
