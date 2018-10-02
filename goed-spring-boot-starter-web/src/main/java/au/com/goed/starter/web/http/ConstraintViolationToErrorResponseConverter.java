package au.com.goed.starter.web.http;

import java.util.function.Function;

import javax.validation.ConstraintViolation;

import org.springframework.validation.DefaultMessageCodesResolver;

/**
 * Converts Constraint Violations to an {@link ErrorResponse}.
 * 
 * @author Goed Bezig
 */
public class ConstraintViolationToErrorResponseConverter implements Function<ConstraintViolation<?>, ErrorResponse> {

    private final ErrorCodeMapper errorCodeMapper;
    private final DefaultMessageCodesResolver defaultMessageCodesResolver;

    public ConstraintViolationToErrorResponseConverter(ErrorCodeMapper errorCodeMapper) {
        this.errorCodeMapper = errorCodeMapper;
        this.defaultMessageCodesResolver = new DefaultMessageCodesResolver();
    }

    @Override
    public ErrorResponse apply(ConstraintViolation<?> cv) {
        String violation = cv.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
        String param = cv.getPropertyPath().toString();
        String code = this.errorCodeMapper.mapErrorCodes(defaultMessageCodesResolver.resolveMessageCodes(violation, null, param, null));

        return new ErrorResponse(code, cv.getMessage()).withParameter(findParamName(param));
    }

    private String findParamName(String param) {
        int index = param.lastIndexOf('.');
        return index == -1 ? param : param.substring(index + 1);
    }

}
