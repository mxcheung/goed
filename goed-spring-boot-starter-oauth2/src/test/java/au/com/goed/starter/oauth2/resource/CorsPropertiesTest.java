package au.com.goed.starter.oauth2.resource;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CorsProperties}.
 * 
 * @author Goed Bezig
 *
 */
public class CorsPropertiesTest {

    private CorsProperties properties;
    
    @Before
    public void setup() {
        this.properties = new CorsProperties();
    }
    
    @Test
    public void shouldContainDefaultValuesIfNotModified() {
        assertEquals(CorsProperties.DEFAULT_ALLOWED_CREDENTIALS, this.properties.getAllowedCredentials());
        assertEquals(CorsProperties.DEFAULT_ALLOWED_HEADER, this.properties.getAllowedHeader());
        assertEquals(CorsProperties.DEFAULT_ALLOWED_METHOD, this.properties.getAllowedMethod());
        assertEquals(CorsProperties.DEFAULT_ALLOWED_ORIGIN, this.properties.getAllowedOrigin());
        assertEquals(CorsProperties.DEFAULT_CORS_PATH, this.properties.getCorsPath());
        assertEquals(CorsProperties.DEFAULT_MAX_AGE, this.properties.getMaxAge());
    }
    
    @Test
    public void shouldAllowDefaultValuesToBeModified() {
        this.properties.setAllowedCredentials(false);
        this.properties.setAllowedHeader("header");
        this.properties.setAllowedMethod("method");
        this.properties.setAllowedOrigin("origin");
        this.properties.setCorsPath("path");
        this.properties.setMaxAge(1L);
        
        assertEquals(false, this.properties.getAllowedCredentials());
        assertEquals("header", this.properties.getAllowedHeader());
        assertEquals("method", this.properties.getAllowedMethod());
        assertEquals("origin", this.properties.getAllowedOrigin());
        assertEquals("path", this.properties.getCorsPath());
        assertEquals(new Long(1), this.properties.getMaxAge());
    }
}
