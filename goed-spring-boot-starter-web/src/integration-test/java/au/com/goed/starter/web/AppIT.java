package au.com.goed.starter.web;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.goed.starter.web.http.ErrorResponse;
import au.com.goed.starter.web.http.ErrorResponses;

/**
 * Integration Test for the starter project.
 * 
 * @author Goed Bezig
 */

// To override using test/resources set different property source configuring
// overrides mapping to integration resources.
@TestPropertySource(locations = "classpath:application-integration.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppIT {

    private static final String BASE_URL = "/integration";

    private static final String CODE_500_REASON = "An unknown error has transpired. Please try again.";
    private static final String UNSUPPORTED_MEDIA_MESSAGE = "The content type provided for your request is not supported.";
    private static final String CODE_LENGTH_VIOLATION_REASON = "LENGTH_RULE_VIOLATION";
    private static final String CONFIGURED_TEST_MESSAGE = "this is a test message";
    private static final String CONVERSION_ERROR = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; nested exception is java.lang.NumberFormatException: For input string: \"twenty\"";
    private static final String REQUEST_METHOD_NOT_SUPPORTED_MESSAGE = "Request method 'PUT' not supported";
    private static final String BANDWIDTH_EXCEEDED_MESSAGE = "Bandwidth Exceeded!";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldBeValidPost() {
        ResponseEntity<Response> response = restTemplate.postForEntity(BASE_URL, new AppModel("test me", 19), Response.class);
        assertEquals(CREATED, response.getStatusCode());
    }

    @Test
    public void shouldBeInvalidViaMethodArgumentNotValidException() {
        ResponseEntity<ErrorResponses> response = restTemplate.postForEntity(BASE_URL, new AppModel("test is too big", 19), ErrorResponses.class);
        assertEquals(BAD_REQUEST, response.getStatusCode());
        validateException(CODE_LENGTH_VIOLATION_REASON, CONFIGURED_TEST_MESSAGE, "str", null, response.getBody().getErrorResponses());
    }

    @Test
    public void shouldBeRuntimeError() {
        ResponseEntity<ErrorResponses> response = restTemplate.getForEntity(format("%s?message=runtime error message", BASE_URL),
                ErrorResponses.class, "");
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        validateException(INTERNAL_SERVER_ERROR.name(), CODE_500_REASON, response.getBody().getErrorResponses());
    }

    @Test
    public void shouldBePageNotFoundError() {
        ResponseEntity<ErrorResponses> response = restTemplate.getForEntity(BASE_URL + "/blah", ErrorResponses.class, "");
        assertEquals(NOT_FOUND, response.getStatusCode());
        validateException(NOT_FOUND.name(), NOT_FOUND.getReasonPhrase(), response.getBody().getErrorResponses());
    }

    @Test
    public void shouldBeCustomResponsError() {
        ResponseEntity<ErrorResponses> response = restTemplate.getForEntity(BASE_URL, ErrorResponses.class, "");
        assertEquals(BANDWIDTH_LIMIT_EXCEEDED, response.getStatusCode());
        validateException(BANDWIDTH_LIMIT_EXCEEDED.name(), BANDWIDTH_EXCEEDED_MESSAGE, response.getBody().getErrorResponses());
    }

    @Test
    public void shouldBePageOk() {
        ResponseEntity<ErrorResponses> response = restTemplate.getForEntity(format("%s?str=test me&age=20", BASE_URL), ErrorResponses.class, "");
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldBeArgumentTypeMismatchException() {
        ResponseEntity<ErrorResponses> response = restTemplate.getForEntity(format("%s?str=test me&age=twenty", BASE_URL), ErrorResponses.class, "");
        assertEquals(BAD_REQUEST, response.getStatusCode());
        validateException(BAD_REQUEST.name(), CONVERSION_ERROR, response.getBody().getErrorResponses());
    }

    @Test
    public void shouldHandleRequestMethodNotSupportedException() {
        ResponseEntity<ErrorResponses> response = restTemplate.exchange(BASE_URL, PUT, new HttpEntity<>(null, null), ErrorResponses.class);
        assertEquals(METHOD_NOT_ALLOWED, response.getStatusCode());
        validateException(METHOD_NOT_ALLOWED.name(), REQUEST_METHOD_NOT_SUPPORTED_MESSAGE, response.getBody().getErrorResponses());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeForInvalidContentType() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<ErrorResponses> response = restTemplate.exchange(BASE_URL, POST, request, ErrorResponses.class);
        assertEquals(UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        validateException(UNSUPPORTED_MEDIA_TYPE.name(), UNSUPPORTED_MEDIA_MESSAGE, response.getBody().getErrorResponses());
    }

    @Test
    public void shouldBeConstraintViolation() {
        ResponseEntity<ErrorResponses> response = restTemplate.getForEntity(format("%s?badstr=bad&age=20", BASE_URL), ErrorResponses.class, "");
        assertEquals(BAD_REQUEST, response.getStatusCode());
        validateException(CODE_LENGTH_VIOLATION_REASON, "{test.message}", null, "str", response.getBody().getErrorResponses());
    }

    private void validateException(String code, String reason, List<ErrorResponse> errors) {
        assertEquals(1, errors.size());
        assertEquals(code, errors.get(0).getCode());
        assertEquals(reason, errors.get(0).getReason());
        assertNull(errors.get(0).getCause());
    }

    private void validateException(String code, String reason, String pointer, String parameter, List<ErrorResponse> errors) {
        assertEquals(1, errors.size());
        assertEquals(code, errors.get(0).getCode());
        assertEquals(reason, errors.get(0).getReason());
        assertNotNull(errors.get(0).getCause());
        assertEquals(pointer, errors.get(0).getCause().getPointer());
        assertEquals(parameter, errors.get(0).getCause().getParameter());
    }

}
