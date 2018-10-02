package au.com.goed.starter.oauth2.resource;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Tests for {@link JDBCTokenProperties}.
 * 
 * @author Goed Bezig
 */
public class JDBCTokenPropertiesTest {

    private JDBCTokenProperties properties;
    private Validator validator;

    @Before
    public void setup() {
        this.properties = new JDBCTokenProperties();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldCreateValidProperties() {
        this.properties.setDriver("some.valid.driver");
        this.properties.setPassword("validPass");
        this.properties.setUrl("http://some.url.com");
        this.properties.setUsername("someUser");

        Set<ConstraintViolation<JDBCTokenProperties>> violations = validator.validate(this.properties);
        assertEquals(0, violations.size());
        
        assertEquals("some.valid.driver", this.properties.getDriver());
        assertEquals("validPass", this.properties.getPassword());
        assertEquals("http://some.url.com", this.properties.getUrl());
        assertEquals("someUser", this.properties.getUsername());
    }
    
    @Test
    public void shouldContainViolationsIfDriverPasswordUrlAreNull() {
        this.properties.setDriver(null);
        this.properties.setPassword("validPass");
        this.properties.setUrl(null);
        this.properties.setUsername(null);

        Set<ConstraintViolation<JDBCTokenProperties>> violations = validator.validate(this.properties);
        assertEquals(3, violations.size());
    }
    
    @Test
    public void shouldAcceptNullPassword() {
        this.properties.setDriver("some.valid.driver");
        this.properties.setPassword(null);
        this.properties.setUrl("http://some.url.com");
        this.properties.setUsername("someUser");

        Set<ConstraintViolation<JDBCTokenProperties>> violations = validator.validate(this.properties);
        assertEquals(0, violations.size());
    }
}
