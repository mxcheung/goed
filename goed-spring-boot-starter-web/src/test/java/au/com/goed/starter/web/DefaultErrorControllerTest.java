package au.com.goed.starter.web;

import static au.com.goed.starter.web.DefaultErrorController.ERROR_STATUS_CODE_ATTRIBUTE;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.mock.web.MockHttpServletRequest;

import au.com.goed.starter.web.DefaultErrorController;
import au.com.goed.starter.web.http.ErrorResponses;

/**
 * Units tests for {@link DefaultErrorController}
 * 
 * @author Goed Bezig
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultErrorControllerTest {

    private final static String ERROR_PATH = "/error/path";

    private DefaultErrorController defaultErrorController;

    @Mock
    ErrorAttributes errorAttributesMock;

    @Before
    public void setup() {
        defaultErrorController = new DefaultErrorController(errorAttributesMock);
        setField(defaultErrorController, "path", ERROR_PATH);
        setField(defaultErrorController, "debug", true);
    }

    @Test
    public void shouldBeAbleToObtainConfiguredErrorPath() {
        assertEquals(ERROR_PATH, defaultErrorController.getErrorPath());
    }

    @Test
    public void shouldSetApprorpriateResponseCodeIfErrorStatusCodeExists() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setAttribute(ERROR_STATUS_CODE_ATTRIBUTE, OK.value());
        ErrorResponses response = defaultErrorController.onError(mockRequest);
        assertEquals(OK.name(), response.getErrorResponses().get(0).getCode());
    }

    @Test
    public void shouldSetInternalServerErrorCodeIfErrorStatusCodeNotValid() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setAttribute(ERROR_STATUS_CODE_ATTRIBUTE, 123456);
        ErrorResponses response = defaultErrorController.onError(mockRequest);
        assertEquals(INTERNAL_SERVER_ERROR.name(), response.getErrorResponses().get(0).getCode());
    }
}
