package au.com.goed.starter.actuator;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring Boot Auto Configuration for library.
 * 
 * @author Goed Bezig
 *
 */
@Configuration
@PropertySource("classpath:goedActuatorDefaults.properties")
public class ActuatorConfiguration {

    // Can overwrite custom endpoint name if you wish, else will be listAll.
    @Value("${actuator.custom.list.all:listAll}")
    private String listAllName;
    
    /**
     * Enable the custom list all actuator.
     * @param endpoints all the endpoints available in the system.
     * @return custom actuator that will list config for all actuators.
     */
    @Bean
    public ListEndpoints endpoints(@SuppressWarnings("rawtypes") List<Endpoint> endpoints) {
        return new ListEndpoints(listAllName, endpoints);
    }
}
