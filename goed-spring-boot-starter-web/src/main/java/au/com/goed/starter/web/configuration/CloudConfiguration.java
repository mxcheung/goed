package au.com.goed.starter.web.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import au.com.goed.starter.profile.CLOUD;
import au.com.goed.starter.profile.DEVELOPMENT;
import au.com.goed.starter.profile.PRODUCTION;
import au.com.goed.starter.profile.UAT;

/**
 * <p>
 * Configuration class for enabling Eureka and Hystrix dependant on profile that is active. Note that dev,
 * uat, and prod environments will automatically have cloud enablement (service discovery and hystrix), for
 * other environments by including the 'cloud' profile then cloud enablement will activate.
 * </p>
 * <p>
 * For example by setting: <i>spring.profiles.active=local,cloud</i> you'll enable the local environment to
 * be cloud enabled.
 * </p>
 * <p>
 * Important: Ordering of @EnableEurekaClient and @EnableCircuitBreaker needs to happen with or before WebConfiguration.class is configured or
 * else they aren't activated.
 * </p>
 * 
 * @author Goed Bezig
 */
@DEVELOPMENT
@UAT
@PRODUCTION
@CLOUD
@Configuration
@EnableEurekaClient
@EnableCircuitBreaker
@AutoConfigureBefore(WebConfiguration.class)
class CloudConfiguration {

    /**
     * Prepare a Ribbon aware RestTemplate (@LoadBalanced) that can be used in the application.
     * This allows for calls to other services registered with Eureka to be able to be called via
     * their service ID.
     * @return Eureka Aware {@link RestTemplate}.
     */
    @LoadBalanced
    @Bean
    @Qualifier("sdRestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    
    /**
     * For production environments enable sleuth sampling to be available. Currently will sample everything,
     * may be good to configure a percentage sampler in the future, but as our application usage is quite
     * small overall at present this should not cause any expected issues.
     * 
     * @return {@link AlwaysSampler}.
     */
    @Bean
    @PRODUCTION
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
}
