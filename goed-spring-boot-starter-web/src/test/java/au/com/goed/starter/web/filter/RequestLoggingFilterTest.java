package au.com.goed.starter.web.filter;

import static au.com.goed.starter.web.filter.RequestLoggingFilter.LOG_ALL;
import static au.com.goed.starter.web.filter.RequestLoggingFilter.LOG_POST_REQUEST;
import static au.com.goed.starter.web.filter.RequestLoggingFilter.LOG_PRE_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for {@link RequestLoggingFilter}.
 * 
 * @author Goed Bezig
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestLoggingFilterTest {

    private final static String TEST_LOG_DATA = "Hello World!";

    private RequestLoggingFilter filter;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private Log loggerMock;

    @Captor
    private ArgumentCaptor<String> logCaptorPre;

    @Captor
    private ArgumentCaptor<String> logCaptorPost;

    @Before
    public void setup() {
        filter = new RequestLoggingFilter();
        setField(filter, "logger", loggerMock);
    }

    @Test
    public void shouldNotLogIfModeIsNotValid() {
        setField(filter, "mode", "OFF");
        assertFalse(filter.shouldLog(requestMock));

        filter.beforeRequest(requestMock, TEST_LOG_DATA);
        verify(loggerMock, never()).info(any());

        filter.afterRequest(requestMock, TEST_LOG_DATA);
        verify(loggerMock, never()).info(any());
    }

    @Test
    public void shouldLogBeforeOnlyIfPreSet() {
        setField(filter, "mode", LOG_PRE_REQUEST);
        assertTrue(filter.shouldLog(requestMock));

        filter.beforeRequest(requestMock, TEST_LOG_DATA);
        verify(loggerMock, times(1)).info(logCaptorPre.capture());
        assertEquals(TEST_LOG_DATA, logCaptorPre.getValue());
    }

    @Test
    public void shouldLogPostOnlyIfPostSet() {
        setField(filter, "mode", LOG_POST_REQUEST);
        assertTrue(filter.shouldLog(requestMock));

        filter.afterRequest(requestMock, TEST_LOG_DATA);
        verify(loggerMock, times(1)).info(logCaptorPost.capture());
        assertEquals(TEST_LOG_DATA, logCaptorPost.getValue());
    }

    @Test
    public void shouldLogPreAndPostIfAllSet() {
        setField(filter, "mode", LOG_ALL);
        assertTrue(filter.shouldLog(requestMock));

        filter.beforeRequest(requestMock, TEST_LOG_DATA);
        verify(loggerMock, times(1)).info(logCaptorPre.capture());
        assertEquals(TEST_LOG_DATA, logCaptorPre.getValue());

        filter.afterRequest(requestMock, TEST_LOG_DATA);
        verify(loggerMock, times(2)).info(logCaptorPost.capture());
        assertEquals(TEST_LOG_DATA, logCaptorPost.getValue());
    }
}
