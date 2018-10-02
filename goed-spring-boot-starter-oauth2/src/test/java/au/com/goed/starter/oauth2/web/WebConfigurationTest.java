package au.com.goed.starter.oauth2.web;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link WebConfiguration}.
 * 
 * @author Goed Bezig
 */
public class WebConfigurationTest {

    private WebConfiguration configuration;
    
    @Before
    public void setup() {
        this.configuration = new WebConfiguration();
    }
    
    @Test
    public void shouldCreateDefaultSecurityControllerAdvice() {
        DefaultSecurityControllerAdvice advice = this.configuration.defaultSecurityControllerAdvice();
        assertNotNull(advice);
    }
}
