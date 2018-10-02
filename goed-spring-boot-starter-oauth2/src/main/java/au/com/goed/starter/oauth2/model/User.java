package au.com.goed.starter.oauth2.model;

import static java.lang.String.format;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * User model representation of JWT token data.
 * 
 * @author Goed Bezig
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "user_name", "first_name", "surname", "email", "scope", "authorities" })
public final class User {

    public static final String ANONYMOUS_USER = "ANONYMOUS";

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @JsonProperty("user_name")
    private String username;

    @JsonProperty("first_name")
    private String firstname;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("scope")
    private ArrayList<String> scopes = new ArrayList<String>();

    @JsonProperty("authorities")
    private ArrayList<String> authorities = new ArrayList<String>();

    @JsonProperty("attributes")
    private HashMap<String, String> attributes = new HashMap<String, String>();

    private User() {
    }

    public static User fromJson(String json) {
        try {
            return MAPPER.readValue(json, User.class);
        } catch (Exception e) {
            LOGGER.error(format("Error mapping JWT to User => json: %s\nReturning Anonymous User instead.", json), e);
        }

        return anonymousUser();
    }

    public static User fromPrincipal(Principal principal) {
        User user = new User();
        user.username = principal.getName();
        return user;
    }

    public static User anonymousUser() {
        User user = new User();
        user.username = ANONYMOUS_USER;
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getScopes() {
        return scopes;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

}
