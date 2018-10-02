package au.com.goed.starter.oauth2.resource;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * Unit tests for {@link RemoteTokenConfiguration}.
 * 
 * @author Goed Bezig
 */
@RunWith(MockitoJUnitRunner.class)
public class RemoteTokenConfigurationTest {

    private RemoteTokenConfiguration configuration;

    @Spy
    private RemoteTokenProperties properties;

    @Before
    public void setup() throws Exception {
        this.configuration = new RemoteTokenConfiguration();
        when(this.properties.getEndpoint()).thenReturn(new URI("http://some.uri.com"));
        when(this.properties.getClientId()).thenReturn("client id");
        when(this.properties.getSecret()).thenReturn("secret");
    }

    @Test
    public void shouldCreateValidRemoteTokenServices() {
        RemoteTokenServices rts = this.configuration.tokenService(this.properties);
        assertNotNull(rts);

        verify(properties, times(1)).getClientId();
        verify(properties, times(1)).getEndpoint();
        verify(properties, times(1)).getSecret();
    }
}
