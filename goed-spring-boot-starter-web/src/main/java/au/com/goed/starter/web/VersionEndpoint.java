package au.com.goed.starter.web;

import static com.google.common.base.Joiner.on;
import static java.lang.String.format;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.core.env.Environment;

/**
 * Custom Version Endpoint in order to easily identify what version is currently running for the service.
 * 
 * @author Goed Bezig
 */
public class VersionEndpoint implements Endpoint<String> {

    private static final String ENDPOINT_NAME = "version";

    private final Environment environment;

    public VersionEndpoint(final Environment environment) {
        super();
        this.environment = environment;
    }

    @Override
    public String getId() {
        return ENDPOINT_NAME;
    }

    @Override
    public String invoke() {
        return format("%s : %s (%s)", environment.getProperty("application.title"), environment.getProperty("application.version"),
                on(",").join(environment.getActiveProfiles()));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

}
