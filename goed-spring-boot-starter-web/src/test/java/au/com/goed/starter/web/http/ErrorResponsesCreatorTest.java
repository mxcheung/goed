package au.com.goed.starter.web.http;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Unit tests for {@link ErrorResponsesCreator}.
 * 
 * @author Goed Bezig
 */
@RunWith(MockitoJUnitRunner.class)
public class ErrorResponsesCreatorTest {

    private final static String TEST_CODE = "testCode";
    private final static String TEST_REASON = "test reason";

    private final static String TEST_CODE_2 = "testCode2";
    private final static String TEST_REASON_2 = "test reason 2";

    private ErrorResponsesCreator errorResponsesCreator;

    @Mock
    private ErrorCodeMapper errorCodeMapperMock;

    @Mock
    private ConstraintViolationToErrorResponseConverter constraintViolationConverterMock;

    @Mock
    private ConstraintViolation<?> constraintViolationMock;

    @Mock
    private BindingResult bindingResultMock;

    @Mock
    private FieldError fieldErrorMock;

    @Mock
    private ObjectError objectErrorMock;

    @Before
    public void setup() {
        this.errorResponsesCreator = new ErrorResponsesCreator(errorCodeMapperMock, constraintViolationConverterMock);
    }

    @Test
    public void shouldCreateErrorResponsesGiveCodeAndReason() {
        ErrorResponses responses = errorResponsesCreator.createErrorResponses(TEST_CODE, TEST_REASON);

        assertEquals(1, responses.getErrorResponses().size());
        assertEquals(TEST_CODE, responses.getErrorResponses().get(0).getCode());
        assertEquals(TEST_REASON, responses.getErrorResponses().get(0).getReason());
    }

    @Test
    public void shouldCreateErrorResponsesGivenMultipleErrorResponseItems() {
        ErrorResponse response1 = new ErrorResponse(TEST_CODE, TEST_REASON);
        ErrorResponse response2 = new ErrorResponse(TEST_CODE_2, TEST_REASON_2);

        ErrorResponses responses = errorResponsesCreator.createErrorResponses(response1, response2);
        assertEquals(2, responses.getErrorResponses().size());
        assertEquals(TEST_CODE, responses.getErrorResponses().get(0).getCode());
        assertEquals(TEST_REASON, responses.getErrorResponses().get(0).getReason());
        assertEquals(TEST_CODE_2, responses.getErrorResponses().get(1).getCode());
        assertEquals(TEST_REASON_2, responses.getErrorResponses().get(1).getReason());
    }

    @Test
    public void shouldCreateErrorResponsesGivenListOfErrorResponseItems() {
        ErrorResponse response1 = new ErrorResponse(TEST_CODE, TEST_REASON);
        ErrorResponse response2 = new ErrorResponse(TEST_CODE_2, TEST_REASON_2);

        ArrayList<ErrorResponse> list = new ArrayList<ErrorResponse>();
        list.add(response1);
        list.add(response2);

        ErrorResponses responses = errorResponsesCreator.createErrorResponses(list);
        assertEquals(2, responses.getErrorResponses().size());
        assertEquals(TEST_CODE, responses.getErrorResponses().get(0).getCode());
        assertEquals(TEST_REASON, responses.getErrorResponses().get(0).getReason());
        assertEquals(TEST_CODE_2, responses.getErrorResponses().get(1).getCode());
        assertEquals(TEST_REASON_2, responses.getErrorResponses().get(1).getReason());
    }

    @Test
    public void shouldCreateErrorResponsesFromConstraintViolations() {
        when(constraintViolationConverterMock.apply(any())).thenReturn(new ErrorResponse(TEST_CODE, TEST_REASON));
        HashSet<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(constraintViolationMock);

        ErrorResponses responses = errorResponsesCreator.createErrorResponses(violations);

        assertEquals(1, responses.getErrorResponses().size());
        assertEquals(TEST_CODE, responses.getErrorResponses().get(0).getCode());
        assertEquals(TEST_REASON, responses.getErrorResponses().get(0).getReason());
    }

    @Test
    public void shouldCreateErrorResponsesFromBindingResult() {
        ArrayList<ObjectError> objErrors = new ArrayList<>();
        objErrors.add(objectErrorMock);
        when(objectErrorMock.getCodes()).thenReturn(new String[] { TEST_CODE });
        when(objectErrorMock.getDefaultMessage()).thenReturn(TEST_REASON);
        when(errorCodeMapperMock.mapErrorCodes(eq(new String[] { TEST_CODE }))).thenReturn(TEST_CODE);
        when(bindingResultMock.getGlobalErrors()).thenReturn(objErrors);

        ArrayList<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(fieldErrorMock);
        when(fieldErrorMock.getCodes()).thenReturn(new String[] { TEST_CODE_2 });
        when(fieldErrorMock.getDefaultMessage()).thenReturn(TEST_REASON_2);
        when(errorCodeMapperMock.mapErrorCodes(eq(new String[] { TEST_CODE_2 }))).thenReturn(TEST_CODE_2);
        when(bindingResultMock.getFieldErrors()).thenReturn(fieldErrors);

        ErrorResponses responses = errorResponsesCreator.createErrorResponses(bindingResultMock);
        assertEquals(2, responses.getErrorResponses().size());
        assertEquals(TEST_CODE, responses.getErrorResponses().get(0).getCode());
        assertEquals(TEST_REASON, responses.getErrorResponses().get(0).getReason());
        assertEquals(TEST_CODE_2, responses.getErrorResponses().get(1).getCode());
        assertEquals(TEST_REASON_2, responses.getErrorResponses().get(1).getReason());
    }
}
