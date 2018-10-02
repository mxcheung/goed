package au.com.goed.starter.actuator;

import java.util.List;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;

/**
 * Based on a list all endpoints endpoint from the following tutorial.
 * http://www.baeldung.com/spring-boot-actuators
 * @author Goed Bezig
 *
 */
@SuppressWarnings("rawtypes")
public class ListEndpoints extends AbstractEndpoint<List<Endpoint>> {
    private List<Endpoint> endpoints;
 
    public ListEndpoints(String name, List<Endpoint> endpoints) {
        super(name);
        this.endpoints = endpoints;
    }
 
    public List<Endpoint> invoke() {
        return this.endpoints;
    }
}