package au.com.goed.starter.oauth2.resource;

import static java.util.Optional.ofNullable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import au.com.goed.starter.oauth2.profile.RemoteToken;

/**
 * {@link ConfigurationProperties} for {@code RemoteToken} based properties.
 * 
 * @author Goed Bezig
 */
@RemoteToken
@Configuration
class RemoteTokenConfiguration {

    @Bean
    @ConfigurationProperties("goed.oauth2.token.remote")
    RemoteTokenProperties remoteTokenProperties() {
        return new RemoteTokenProperties();
    }
  
    @Bean
    @Primary
    public RemoteTokenServices tokenService(final RemoteTokenProperties tokenProperties) {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(tokenProperties.getEndpoint().toString());
        tokenService.setClientId(tokenProperties.getClientId());
        tokenService.setClientSecret(ofNullable(tokenProperties.getSecret()).orElse(""));
        return tokenService;
    }
}