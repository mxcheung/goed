package au.com.goed.starter.actuator;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.endpoint.Endpoint;

import au.com.goed.starter.actuator.ListEndpoints;

/**
 * Unit tests for {@link ListEndpoints}.
 * 
 * @author Goed Bezig
 */
@SuppressWarnings("rawtypes")
public class ListEndpointsTest {

    private ListEndpoints endpoints;
    private List<Endpoint> listEndpoints;
    private Endpoint endpointMock1;
    private Endpoint endpointMock2;

    @Before
    public void setup() {
        this.endpointMock1 = mock(Endpoint.class);
        this.endpointMock2 = mock(Endpoint.class);
        this.listEndpoints = new ArrayList<>();
        this.listEndpoints.add(endpointMock1);
        this.listEndpoints.add(endpointMock2);

        this.endpoints = new ListEndpoints("somename", this.listEndpoints);
    }

    @Test
    public void shouldReturnAllConfiguredEndpointsOnInvoke() {
        List<Endpoint> list = endpoints.invoke();
        assertEquals(2, list.size());
        assertThat(list, hasItems(endpointMock1, endpointMock2));
    }
}
