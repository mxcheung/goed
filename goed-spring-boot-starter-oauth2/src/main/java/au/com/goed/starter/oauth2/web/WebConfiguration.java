package au.com.goed.starter.oauth2.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Bean Configurations setup for project inheriting oauth2 starter. Currently just instanciates the 
 * Default Security Controller Advice for handling security error(s) and decoding JWT token.  See
 * {@link DefaultSecurityControllerAdvice}.
 * 
 * @author Goed Bezig
 */
@Configuration
public class WebConfiguration {

    /**
     * Creates {@link DefaultSecurityControllerAdvice} for handling potential oauth2 exceptions as well as
     * decoding JWT token and adding as a model attribute.
     * 
     * @return {@link DefaultSecurityControllerAdvice}.
     */
    @Bean
    public DefaultSecurityControllerAdvice defaultSecurityControllerAdvice() {
        return new DefaultSecurityControllerAdvice();
    }
    
}
