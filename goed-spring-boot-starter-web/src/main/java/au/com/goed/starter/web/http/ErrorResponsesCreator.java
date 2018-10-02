package au.com.goed.starter.web.http;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.springframework.validation.BindingResult;

/**
 * <p>
 * Creates an {@link ErrorResponses} to be used to display the cause(s) of an error.
 * </p>
 * <p>
 * Error codes are mapped and resolved into cleaner readable messages based on message properties defined in a
 * message resource file
 * </p>
 * 
 * @author Goed Bezig
 */
public class ErrorResponsesCreator {

    private final ErrorCodeMapper errorCodeMapper;
    private final ConstraintViolationToErrorResponseConverter constraintViolationToErrorResponseConverter;

    public ErrorResponsesCreator(ErrorCodeMapper errorCodeMapper,
            ConstraintViolationToErrorResponseConverter constraintViolationToErrorResponseConverter) {
        this.errorCodeMapper = errorCodeMapper;
        this.constraintViolationToErrorResponseConverter = constraintViolationToErrorResponseConverter;
    }

    public ErrorResponses createErrorResponses(BindingResult bindingResult) {
        Stream<ErrorResponse> globalErrors = bindingResult.getGlobalErrors().stream()
                .map(error -> new ErrorResponse(errorCodeMapper.mapErrorCodes(error.getCodes()), error.getDefaultMessage()));

        Stream<ErrorResponse> fieldErrors = bindingResult.getFieldErrors().stream().map(
                error -> new ErrorResponse(errorCodeMapper.mapErrorCodes(error.getCodes()), error.getDefaultMessage()).withPointer(error.getField()));

        return createErrorResponses(concat(globalErrors, fieldErrors).collect(toList()));
    }

    public ErrorResponses createErrorResponses(Set<ConstraintViolation<?>> constraintViolations) {
        return createErrorResponses(constraintViolations.stream().map(constraintViolationToErrorResponseConverter).collect(toList()));
    }

    public ErrorResponses createErrorResponses(String code, String reason) {
        return createErrorResponses(new ErrorResponse(code, reason));
    }

    public ErrorResponses createErrorResponses(ErrorResponse... errorResponse) {
        return createErrorResponses(asList(errorResponse));
    }

    public ErrorResponses createErrorResponses(List<ErrorResponse> errorResponses) {
        return new ErrorResponses(errorResponses);
    }

}
