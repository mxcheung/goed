package au.com.goed.starter.oauth2.resource;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link RemoteTokenProperties}.
 * 
 * @author Goed Bezig
 */
public class RemoteTokenPropertiesTest {

    private RemoteTokenProperties properties;
    private Validator validator;

    @Before
    public void setup() {
        this.properties = new RemoteTokenProperties();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldCreateValidProperties() throws Exception {
        this.properties.setClientId("validId");
        this.properties.setEndpoint(new URI("http://some.valid.uri"));
        this.properties.setSecret("validSecret");

        Set<ConstraintViolation<RemoteTokenProperties>> violations = validator.validate(this.properties);
        assertEquals(0, violations.size());

        assertEquals("validId", this.properties.getClientId());
        assertEquals("http://some.valid.uri", this.properties.getEndpoint().toString());
        assertEquals("validSecret", this.properties.getSecret());
    }

    @Test
    public void shouldFailIfEndpointAndClientIdAreNull() throws Exception {
        this.properties.setClientId(null);
        this.properties.setEndpoint(null);
        this.properties.setSecret("validSecret");

        Set<ConstraintViolation<RemoteTokenProperties>> violations = validator.validate(this.properties);
        assertEquals(2, violations.size());
    }

    @Test
    public void shouldNotFailIfSecretIsNull() throws Exception {
        this.properties.setClientId("validId");
        this.properties.setEndpoint(new URI("http://some.valid.uri"));
        this.properties.setSecret(null);

        Set<ConstraintViolation<RemoteTokenProperties>> violations = validator.validate(this.properties);
        assertEquals(0, violations.size());
    }

}
