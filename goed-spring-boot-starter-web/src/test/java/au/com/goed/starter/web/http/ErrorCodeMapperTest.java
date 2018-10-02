package au.com.goed.starter.web.http;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import static au.com.goed.starter.web.http.ErrorCodeMapper.DEFAULT_ERROR_CODE;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link ErrorCodeMapper}.
 * 
 * @author Goed Bezig
 */
public class ErrorCodeMapperTest {

    private static final String MESSAGE_PROPERTIES_BASE = "messages";
    private static final String TEST_CODE = "TestCode";
    private static final String TEST_CODE_MESSAGE = "Test Code Message";

    private ErrorCodeMapper errorCodeMapper;
    private ResourceBundleMessageSource messages;

    @Before
    public void setup() {
        this.messages = new ResourceBundleMessageSource();
        this.messages.setBasename(MESSAGE_PROPERTIES_BASE);

        this.errorCodeMapper = new ErrorCodeMapper(this.messages);
    }

    @Test
    public void shouldMapConfiguredErrorCode() throws Exception {
        String message = this.errorCodeMapper.mapErrorCodes(new String[] { TEST_CODE });
        assertEquals(TEST_CODE_MESSAGE, message);
    }

    @Test
    public void shouldReturnDefaultIfErrorCodeNotConfigured() throws Exception {
        String message = this.errorCodeMapper.mapErrorCodes(new String[] { "non configured code" });
        assertEquals(DEFAULT_ERROR_CODE, message);
    }
}
