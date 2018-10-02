package au.com.goed.starter.oauth2.resource;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Unit tests for {@link JDBCTokenConfiguration}.
 * 
 * @author Goed Bezig
 */
@RunWith(MockitoJUnitRunner.class)
public class JDBCTokenConfigurationTest {

    private JDBCTokenConfiguration configuration;

    @Mock
    private JDBCTokenProperties propertiesMock;

    @Before
    public void setup() {
        this.configuration = new JDBCTokenConfiguration();

        when(this.propertiesMock.getDriver()).thenReturn("org.h2.Driver");
        when(this.propertiesMock.getPassword()).thenReturn("password");
        when(this.propertiesMock.getUsername()).thenReturn("username");
        when(this.propertiesMock.getUrl()).thenReturn("jdbc://some.url");
    }

    @Test
    public void shouldCreateTokenStore() {
        TokenStore tokenStore = this.configuration.tokenStore(propertiesMock);
        assertNotNull(tokenStore);
    }

    @Test
    public void shouldCreateTokenServices() {
        DefaultTokenServices services = this.configuration.tokenServices(propertiesMock);
        assertNotNull(services);
    }

    @Test
    public void shouldCreateDataSource() {
        DataSource dataSource = this.configuration.dataSource(propertiesMock);
        assertNotNull(dataSource);
    }
}
