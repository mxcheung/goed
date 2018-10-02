package au.com.goed.starter.oauth2.web;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SecurityErrorResponseTest {

    private final static String JSON_RESPONSE = "{\"code\":\"SOME_CODE\",\"reason\":\"some reason\"}";
    
    private ObjectMapper mapper;
    
    @Before
    public void setup() {
        this.mapper = new ObjectMapper();
    }
    
    @Test
    public void shouldSerialize() throws Exception {
        SecurityErrorResponse response = new SecurityErrorResponse("SOME_CODE", "some reason");
        String json = this.mapper.writeValueAsString(response);
        assertEquals(json, JSON_RESPONSE);
    }
    
    @Test
    public void shouldDeserialize() throws Exception {
        SecurityErrorResponse response = this.mapper.readValue(JSON_RESPONSE, SecurityErrorResponse.class);
        assertEquals("SOME_CODE", response.getCode());
        assertEquals("some reason", response.getReason());
    }
}
