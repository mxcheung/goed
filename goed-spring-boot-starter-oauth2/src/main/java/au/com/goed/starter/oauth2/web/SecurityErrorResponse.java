package au.com.goed.starter.oauth2.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Allows standard error display in JSON form.
 * 
 * @author Goed Bezig
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "code", "reason" })
public class SecurityErrorResponse {

    private String code;
    private String reason;

    /*
     * Package Private for deserialzation purposes.
     */
    SecurityErrorResponse() {    
    }
    
    public SecurityErrorResponse(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
