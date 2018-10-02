package au.com.goed.starter.oauth2.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.Test;

/**
 * Unit test for {@link User}.
 * 
 * @author Goed Bezig
 *
 */
public class UserTest {

    private final static String JSON_USER = "{"
            + "\"user_name\": \"Lord Vader\","
            + "\"first_name\": \"Anakin\","
            + "\"surname\": \"Skywalker\","
            + "\"scope\": [\"read\"],"
            + "\"authorities\": [\"SITH_LORD\"],"
            + "\"email\": \"lord.vader@deathstar.net\","
            + "\"attributes\": {\"BSEG\":\"LR\"}"
            + "}";
    
    
    @Test
    public void shouldCreateUserFromValidJSON() {
        User user = User.fromJson(JSON_USER);
        assertEquals(user.getEmail(), "lord.vader@deathstar.net");
        assertEquals(user.getFirstname(), "Anakin");
        assertEquals(user.getSurname(), "Skywalker");
        assertEquals(user.getUsername(), "Lord Vader");
        assertThat(user.getAuthorities(), containsInAnyOrder("SITH_LORD"));
        assertThat(user.getScopes(), containsInAnyOrder("read"));
        assertThat(user.getAttributes(), hasEntry("BSEG", "LR"));
    }
    
    @Test
    public void shouldCreateAnonymousUserFromInvalidJSON() {
        User user = User.fromJson("blah");
        assertEquals(user.getEmail(), null);
        assertEquals(user.getFirstname(), null);
        assertEquals(user.getSurname(), null);
        assertEquals(user.getUsername(), User.ANONYMOUS_USER);
        assertEquals(user.getAuthorities().size(), 0);
        assertEquals(user.getScopes().size(), 0);
        assertEquals(user.getAttributes().size(), 0);
    }
    
    @Test
    public void shouldCreateUserFromPrincipal() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Lord Vader");
        User user = User.fromPrincipal(principal);
        assertEquals(user.getUsername(), "Lord Vader");
    }
    
    @Test
    public void shouldCreateAnonymousUser() {
        User user = User.anonymousUser();
        assertEquals(user.getUsername(), User.ANONYMOUS_USER);
    }
}
