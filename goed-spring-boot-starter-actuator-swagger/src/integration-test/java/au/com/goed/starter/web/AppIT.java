package au.com.goed.starter.web;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for package. Validate Actuator and Swagger endpoints are working.
 * 
 * @author Goed Bezig
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppIT {

    private static final String BASE_URL = "/integration";

    private static final String HEALTH_ACTUATOR_URL = "/_admin_/health";
    private static final String HEALTH_ACTUATOR_RESULT = "{\"status\":\"UP\"}";
    
    private static final String SWAGGER_URL = "/swagger-ui.html";

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * This is just a test to see the test app is working.
     */
    @Test
    public void shouldGetMessage() {
        ResponseEntity<AppModel> response = restTemplate.getForEntity(format("%s?message=hello world", BASE_URL), AppModel.class, "");
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldValidateActuatorWorking() {
        ResponseEntity<String> response = restTemplate.getForEntity(HEALTH_ACTUATOR_URL, String.class);
        assertEquals(OK, response.getStatusCode());
        assertEquals(response.getBody(), HEALTH_ACTUATOR_RESULT);
    }
    
    @Test
    public void shouldValidateSwaggerWorking() {
        ResponseEntity<String> response = restTemplate.getForEntity(SWAGGER_URL, String.class);
        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody().contains("springfox-swagger"));
        assertTrue(response.getBody().contains("swagger-ui"));
        
    }
}
