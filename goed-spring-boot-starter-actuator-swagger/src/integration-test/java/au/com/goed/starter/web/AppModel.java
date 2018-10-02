package au.com.goed.starter.web;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for test application used in integration testing.
 * 
 * @author Goed Bezig
 */
public class AppModel {
    
    @Length(min = 1, max = 10)
    private String message;

    @JsonCreator
    public AppModel(@JsonProperty("message") String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
