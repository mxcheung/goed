package au.com.goed.starter.web;

import static java.util.Locale.getDefault;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;
import static org.springframework.util.StringUtils.hasText;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import au.com.goed.starter.web.http.ErrorResponses;
import au.com.goed.starter.web.http.ErrorResponsesCreator;

/**
 * Default rest exception controller advice for handling common rest controller derived thrown exceptions. As
 * per Spring Boot error handling, exceptions handled when using this library will be handled in order of:
 * <ol>
 * <li>Exception handling defined within the controller itself.</li>
 * <li>Controller Advice exception handlers with a higher precedence set than this class.</li>
 * <li>This class.</li>
 * <li>{@link org.springframework.boot.autoconfigure.web.BasicErrorController}</li>
 * </ol>
 * 
 * @author Goed Bezig
 */
@RestControllerAdvice
@Order(LOWEST_PRECEDENCE)
public class DefaultExceptionControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionControllerAdvice.class);

    private static final String ERROR_MEDIA_KEY = "error.unsupported.media";
    private static final String ERROR_GENERIC_KEY = "error.generic";

    private final ErrorResponsesCreator errorResponsesCreator;

    private final MessageSource messageSource;

    @Autowired
    public DefaultExceptionControllerAdvice(ErrorResponsesCreator errorResponsesCreator, MessageSource messageSource) {
        this.errorResponsesCreator = errorResponsesCreator;
        this.messageSource = messageSource;

        LOGGER.info("Instanciated => {}", this.getClass().getCanonicalName());
        LOGGER.info("\t|_ ErrorResponseCreator => {}", errorResponsesCreator.toString());
        LOGGER.info("\t|_ MessageSource => {}", messageSource.toString());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponses handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LOGGER.info("MethodArgumentNotValidException being handled: {}", ex.getMessage());
        return errorResponsesCreator.createErrorResponses(ex.getBindingResult());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponses handleConstraintViolationException(ConstraintViolationException ex) {
        LOGGER.info("ConstraintViolationException being handled: {}", ex.getMessage());
        return errorResponsesCreator.createErrorResponses(ex.getConstraintViolations());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({ UnsatisfiedServletRequestParameterException.class, MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class, ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class })
    public ErrorResponses handleClientException(Exception ex) {
        LOGGER.info("Client type exception being handled: {}", ex.getMessage());
        return errorResponsesCreator.createErrorResponses(BAD_REQUEST.name(), ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponses handleHttpRequestMethodNotSupportedException(Exception ex) {
        LOGGER.info("HttpRequestMethodNotSupportedException being handled: {}", ex.getMessage());
        return errorResponsesCreator.createErrorResponses(METHOD_NOT_ALLOWED.name(), ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class })
    public ErrorResponses handleHttpMediaTypeException(HttpMediaTypeNotSupportedException e) {
        LOGGER.info("Media Type Exception being handled: {}", e.getMessage());
        return errorResponsesCreator.createErrorResponses(UNSUPPORTED_MEDIA_TYPE.name(),
                messageSource.getMessage(ERROR_MEDIA_KEY, null, getDefault()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponses> handleException(Throwable t) {
        ResponseStatus status = t.getClass().getAnnotation(ResponseStatus.class);
        if (status != null) {
            LOGGER.info("Non Specific Exception being handled: {}", t.getMessage());
            return new ResponseEntity<>(
                    errorResponsesCreator.createErrorResponses(status.value().name(), hasText(status.reason()) ? status.reason() : t.getMessage()),
                    status.value());
        }

        LOGGER.error("Exception being handled: {}", t);
        return new ResponseEntity<>(errorResponsesCreator.createErrorResponses(INTERNAL_SERVER_ERROR.name(),
                messageSource.getMessage(ERROR_GENERIC_KEY, null, getDefault())), INTERNAL_SERVER_ERROR);
    }
}
