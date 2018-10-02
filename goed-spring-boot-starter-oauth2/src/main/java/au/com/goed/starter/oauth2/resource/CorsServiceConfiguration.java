package au.com.goed.starter.oauth2.resource;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>Cross Origin Resource Sharing (CORS) configuration for the inheriting application.</p>
 * <p>Obtains the necessary configuration from {@link CorsProperties} and instanciates the once per
 * request {@link CorsFilter} with highest precedence.</p>
 * 
 * @author Goed Bezig
 *
 */
@Configuration
class CorsServiceConfiguration {
    
    /**
     * CORS configuration properties for the application.
     * @return {@link CorsProperties}.
     */
    @Bean
    @ConfigurationProperties("goed.cors")
    public CorsProperties corsProperties() {
        return new CorsProperties();
    }
    
    /**
     * Configure Url Based CORS configuration.
     * @param corsProperties {@link CorsProperties} specifying CORS configuration properties for the application.
     * @return {@link UrlBasedCorsConfigurationSource}.
     */
    @Bean
    public UrlBasedCorsConfigurationSource configurationSource(final CorsProperties corsProperties) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(corsProperties.getAllowedCredentials());
        config.addAllowedOrigin(corsProperties.getAllowedOrigin());
        config.addAllowedHeader(corsProperties.getAllowedHeader());
        config.addAllowedMethod(corsProperties.getAllowedMethod());
        config.setMaxAge(corsProperties.getMaxAge()); 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperties.getCorsPath(), config);
        return source;
    }

    /**
     * CORS filter for the application, set as Highest Precendence to ensure it is run.
     */
    @Configuration
    @Order(HIGHEST_PRECEDENCE)
    class ResourceCorsFilter extends CorsFilter {

        @Autowired
        ResourceCorsFilter(final UrlBasedCorsConfigurationSource source) {
            super(source);
        }

    }
}
