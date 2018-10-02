package au.com.goed.starter.oauth2.resource;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import au.com.goed.starter.profile.CLOUD;
import au.com.goed.starter.profile.DEVELOPMENT;
import au.com.goed.starter.profile.PRODUCTION;
import au.com.goed.starter.profile.UAT;

/**
 * <p>
 * Dependent on profile enables inheriting application to become an Oauth2 Resource server.
 * </p>
 * <p>
 * Furthermore provides a default {@link WebSecurityConfigurerAdapter} that forces all endpoints to be
 * authenticated. If an application provides their own {@link WebSecurityConfigurerAdapter} implementation
 * this one will be ignored.
 * </p>
 * <p>
 * Also enables the inheriting application to be allowed to use @PreAuthorize and @PostAuthorize annotations.
 * </p>
 * 
 * @author Goed Bezig
 */
@DEVELOPMENT
@PRODUCTION
@UAT
@CLOUD
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class Oauth2ResourceConfiguration {

    /**
     * Provides a default {@link WebSecurityConfigurerAdapter} implementation that
     * requires all access to be authenticated.
     */
    @Configuration
    @Order(LOWEST_PRECEDENCE - 1)
    class Oauth2WebSecurityAdapter extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(final HttpSecurity http) throws Exception {
            
            http.sessionManagement().sessionCreationPolicy(STATELESS).and().csrf().disable();

            http.authorizeRequests().anyRequest().authenticated();

            http.formLogin().disable();

            http.logout().disable();  
        }
        
        @Override
        public void configure(WebSecurity web) throws Exception {
            // If inheriting actuator starter project enable endpoints to be accessible.
            web.ignoring().antMatchers("/_admin_/**");
        }
    }
}
