package au.com.goed.starter.oauth2.resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Remote Token Profile based testing to validate that necessary configurations are activated via the remoteToken
 * profile.
 * 
 * @author Goed Bezig
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("remoteToken")
@TestPropertySource(properties = { 
        "goed.oauth2.token.remote.clientId=clientId",
        "goed.oauth2.token.remote.endpoint=http://localhost:9999/blah"
})
public class RemoteTokenProfileIT {

    @Autowired(required = false)
    private SwaggerConsoleConfiguration swaggerConsoleConfiguration;
    
    @Autowired(required = false)
    private Oauth2ResourceConfiguration oauth2ResourceConfiguration;
    
    @Autowired(required = false)
    private CorsServiceConfiguration corsServiceConfiguration;
    
    @Autowired(required = false)
    private JDBCTokenConfiguration jdbcTokenConfiguration;
    
    @Autowired(required = false)
    private RemoteTokenConfiguration remoteTokenConfiguration;
    
    @Test
    public void shouldCreateConfigurations() throws Exception { 
        assertNotNull(corsServiceConfiguration);
        assertNotNull(remoteTokenConfiguration);
    }
    
    @Test
    public void shouldNotCreateConfigurations() throws Exception {
        assertNull(swaggerConsoleConfiguration);
        assertNull(oauth2ResourceConfiguration); 
        assertNull(jdbcTokenConfiguration);
    }
}
