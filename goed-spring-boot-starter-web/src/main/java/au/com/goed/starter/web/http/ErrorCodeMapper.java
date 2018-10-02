package au.com.goed.starter.web.http;

import static io.vavr.control.Try.success;
import static java.util.Arrays.stream;
import static java.util.Locale.ENGLISH;

import org.springframework.context.MessageSource;

import io.vavr.control.Try;

/**
 * Maps error codes to a readable configured string provided by a message source.
 * 
 * @author Goed Bezig
 */
public class ErrorCodeMapper {

    protected static final String DEFAULT_ERROR_CODE = "DEFAULT CONSTRAINT VIOLATION";

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    private final MessageSource messageSource;

    public ErrorCodeMapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * With the given errorCodes, find the best matching message to be returned.
     * 
     * @param errorCodes the error codes.
     * @return best possible matching configured message.
     */
    public String mapErrorCodes(String[] errorCodes) {
        return stream(errorCodes).map(errorCode -> Try.of(() -> messageSource.getMessage(errorCode, EMPTY_OBJECT_ARRAY, ENGLISH)))
                .filter(Try::isSuccess).findFirst().orElse(success(DEFAULT_ERROR_CODE)).get();
    }
}
