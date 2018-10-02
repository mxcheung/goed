package au.com.goed.starter.oauth2.resource;

import static au.com.goed.starter.constant.Profiles.CLOUD;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Devlopment Profile based testing to validate that necessary configurations are activated via the DEVELOPMENT
 * profile.
 * 
 * @author Goed Bezig
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(CLOUD)
public class CloudProfileIT {

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
        assertNotNull(oauth2ResourceConfiguration);
        assertNotNull(corsServiceConfiguration);
    }
    
    @Test
    public void shouldNotCreateConfigurations() throws Exception {
        assertNull(jdbcTokenConfiguration);
        assertNull(remoteTokenConfiguration);
        assertNull(swaggerConsoleConfiguration);
    }
}

