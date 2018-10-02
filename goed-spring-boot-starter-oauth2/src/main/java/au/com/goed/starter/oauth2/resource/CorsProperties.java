package au.com.goed.starter.oauth2.resource;

/**
 * CORS configuration properties.
 * 
 * @author Goed Bezig
 */
public class CorsProperties {

    static final Boolean DEFAULT_ALLOWED_CREDENTIALS = true;
    static final String DEFAULT_ALLOWED_ORIGIN = "*";
    static final String DEFAULT_ALLOWED_HEADER = "*";
    static final String DEFAULT_ALLOWED_METHOD = "*";
    static final Long DEFAULT_MAX_AGE = 3600L;
    static final String DEFAULT_CORS_PATH = "/**";

    private Boolean allowedCredentials = DEFAULT_ALLOWED_CREDENTIALS;

    private String allowedOrigin = DEFAULT_ALLOWED_ORIGIN;

    private String allowedHeader = DEFAULT_ALLOWED_HEADER;

    private String allowedMethod = DEFAULT_ALLOWED_METHOD;

    private Long maxAge = DEFAULT_MAX_AGE;

    private String corsPath = DEFAULT_CORS_PATH;

    public Boolean getAllowedCredentials() {
        return allowedCredentials;
    }

    public void setAllowedCredentials(Boolean allowedCredentials) {
        this.allowedCredentials = allowedCredentials;
    }

    public String getAllowedOrigin() {
        return allowedOrigin;
    }

    public void setAllowedOrigin(String allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }

    public String getAllowedHeader() {
        return allowedHeader;
    }

    public void setAllowedHeader(String allowedHeader) {
        this.allowedHeader = allowedHeader;
    }

    public String getAllowedMethod() {
        return allowedMethod;
    }

    public void setAllowedMethod(String allowedMethod) {
        this.allowedMethod = allowedMethod;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public String getCorsPath() {
        return corsPath;
    }

    public void setCorsPath(String corsPath) {
        this.corsPath = corsPath;
    }
}
