package au.com.goed.starter.web.http;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link ErrorResponse}.
 * 
 * @author Goed Bezig
 */
public class ErrorResponseTest {

    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void shouldSerialize() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse("code", "reason").withParameter("parameter").withPointer("pointer");

        assertEquals("{\"code\":\"code\",\"reason\":\"reason\",\"cause\":{\"pointer\":\"pointer\"}}", mapper.writeValueAsString(errorResponse));
    }
}