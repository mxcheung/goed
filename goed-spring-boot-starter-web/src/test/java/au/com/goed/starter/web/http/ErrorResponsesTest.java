package au.com.goed.starter.web.http;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link ErrorResponsesTest}
 * 
 * @author Goed Bezig
 */
public class ErrorResponsesTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void shouldSerialize() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse("code", "reason").withParameter("parameter").withPointer("pointer");
        ArrayList<ErrorResponse> list = new ArrayList<>();
        list.add(errorResponse);
        ErrorResponses responses = new ErrorResponses(list);

        assertEquals("{\"errors\":[{\"code\":\"code\",\"reason\":\"reason\",\"cause\":{\"pointer\":\"pointer\"}}]}",
                mapper.writeValueAsString(responses));
    }
}
