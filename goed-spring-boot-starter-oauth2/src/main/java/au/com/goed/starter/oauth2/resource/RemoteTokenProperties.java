package au.com.goed.starter.oauth2.resource;

import java.net.URI;

import javax.validation.constraints.NotNull;

/**
 * {@link ConfigurationProperties} for {@code RemoteToken} based properties.
 * 
 * @author Goed Bezig
 */
class RemoteTokenProperties {

    @NotNull
    private URI endpoint;
    
    @NotNull
    private String clientId;
    
    private String secret;

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    
}
