package au.com.goed.starter.oauth2.resource;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import au.com.goed.starter.profile.DEVELOPMENT;
import au.com.goed.starter.profile.LOCAL;
import au.com.goed.starter.profile.UAT;

/**
 * Configuration for enabling the Swagger UI console (if configured). Will configure Swagger Console when the
 * environment profile is 'local' or 'uat' or 'dev' and the 'SwaggerDocket' bean has been created (found in
 * goed-spring-boot-starter-actuator-swagger).
 * 
 * @author Goed Bezig
 */
@LOCAL
@UAT
@DEVELOPMENT
@Configuration
public class SwaggerConsoleConfiguration {

    /**
     * Security configuration for Swagger Console, loaded before {@link Oauth2WebSecurityAdapter}.
     * @author Goed Bezig
     *
     */
    @Configuration
    @Order(LOWEST_PRECEDENCE - 2)
    public class SwaggerWebSecurityAdapter extends WebSecurityConfigurerAdapter {

        /**
         * To enable the swagger resource to be viewed we ignore security for the necessary ant patterns
         * utilized by Swagger UI.
         */
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**");
        }
    }
}
