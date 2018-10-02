package au.com.goed.starter.web;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import au.com.goed.starter.web.http.ErrorResponse;
import au.com.goed.starter.web.http.ErrorResponses;
import io.vavr.control.Try;

/**
 * <p>
 * Controller used to override Spring Boots registration of the
 * {@link org.springframework.boot.autoconfigure.web.BasicErrorController}, which generates the familiar white
 * error label page.
 * </p>
 * <p>
 * This error controller implementation enforces that the error responses returned are in JSON form.
 * </p>
 * 
 * @author Goed Bezig
 */
@RestController
public class DefaultErrorController implements ErrorController {

    protected static final String ERROR_STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";

    @Value("${error.path:/error}")
    private String path;

    @Value("${debug.enabled:false}")
    private boolean debug;

    private ErrorAttributes errorAttributes;

    public DefaultErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return path;
    }

    /**
     * Handle requests that have been led to the error path.
     * 
     * @param request the request that has generated an error.
     * @return restful error response representation.
     */
    @RequestMapping(value = "${error.path:/error}", produces = APPLICATION_JSON_VALUE)
    public ErrorResponses onError(HttpServletRequest request) {

        Map<String, Object> attrMap = errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), this.debug);
        HttpStatus status = Try.of(() -> HttpStatus.valueOf((Integer) request.getAttribute(ERROR_STATUS_CODE_ATTRIBUTE)))
                .getOrElse(INTERNAL_SERVER_ERROR);
        return new ErrorResponses(asList(new ErrorResponse(status.name(), (String) attrMap.get("error"))));
    }

}
