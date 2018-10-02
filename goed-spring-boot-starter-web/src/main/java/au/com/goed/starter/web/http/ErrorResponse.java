package au.com.goed.starter.web.http;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * POJO depicting an Error Response body.
 * 
 * @author Goed Bezig
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String code;
    private String reason;
    private Cause cause;

    private ErrorResponse() {
    }

    public ErrorResponse(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public ErrorResponse withPointer(String pointer) {
        this.cause = new Cause(pointer, null);
        return this;
    }

    public ErrorResponse withParameter(String parameter) {
        this.cause = new Cause(null, parameter);
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public Cause getCause() {
        return cause;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Cause {
        private String pointer;
        private String parameter;

        private Cause() {
        }

        public Cause(String pointer, String parameter) {
            this.pointer = pointer;
            this.parameter = parameter;
        }

        public String getPointer() {
            return pointer;
        }

        public String getParameter() {
            return parameter;
        }
    }
}
