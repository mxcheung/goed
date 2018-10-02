package au.com.goed.starter.oauth2.resource;

import javax.validation.constraints.NotNull;

/**
 * {@link ConfigurationProperties} for {@code JDBCToken} based properties.
 * 
 * @author Goed Bezig
 */
class JDBCTokenProperties {

    @NotNull
    private String driver;

    @NotNull
    private String url;

    @NotNull
    private String username;

    private String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
