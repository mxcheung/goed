package au.com.goed.starter.web.http;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON Error Response model.
 * 
 * @author Goed Bezig
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponses {

    @JsonProperty("errors")
    private List<ErrorResponse> errorResponses;

    /**
     * Private constructor allows deserialization of the object.
     */
    private ErrorResponses() {
    }

    public ErrorResponses(List<ErrorResponse> errorResponses) {
        this.errorResponses = errorResponses;
    }

    public List<ErrorResponse> getErrorResponses() {
        return errorResponses;
    }
}
