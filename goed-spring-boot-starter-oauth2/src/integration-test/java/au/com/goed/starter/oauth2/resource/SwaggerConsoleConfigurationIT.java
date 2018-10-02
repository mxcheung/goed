package au.com.goed.starter.oauth2.resource;

import static au.com.goed.starter.constant.Profiles.LOCAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.OK;
import static springfox.documentation.builders.PathSelectors.any;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.goed.starter.oauth2.App;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Integration Test to validate that Swagger Console is triggered to be accessible when service
 * is run with a valid profile.
 * 
 * @author Goed Bezig
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {App.class, SwaggerConsoleConfigurationIT.TestConfiguration.class}
        )
@ActiveProfiles(LOCAL)
public class SwaggerConsoleConfigurationIT {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void shouldEnableSwaggerConsole() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/swagger-ui.html", String.class);
        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody().contains("<title>Swagger UI</title>"));
    }
    
    @Configuration
    static class TestConfiguration {

        @Bean(name = "SwaggerDocket")
        public Docket restApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("au.com.goed"))
                    .paths(any())
                    .build();
        }

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("Integration Test")
                    .description("Integration Description")
                    .termsOfServiceUrl("Integration TOS")
                    .license("Integration License")
                    .licenseUrl("Integration License URL")
                    .version("1.0.0-INTEGRATION")
                    .build();
        }
    }

}
