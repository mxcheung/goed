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
 * JDBCToken Profile based testing to validate that necessary configurations are activated via the JDBCToken
 * profile.
 * 
 * @author Goed Bezig
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("jdbcToken")
@TestPropertySource(properties = { 
        "goed.oauth2.token.jdbc.driver=org.h2.Driver",
        "goed.oauth2.token.jdbc.username=username",
        "goed.oauth2.token.jdbc.url=password"
})
public class JDBCTokenProfileIT {

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
        assertNotNull(jdbcTokenConfiguration);
    }
    
    @Test
    public void shouldNotCreateConfigurations() throws Exception {
        assertNull(remoteTokenConfiguration);
        assertNull(swaggerConsoleConfiguration);
        assertNull(oauth2ResourceConfiguration);  
    }
}
