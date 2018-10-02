package au.com.goed.starter.oauth2.web;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;

import au.com.goed.starter.oauth2.model.User;

/**
 * Unit Tests for {@link DefaultSecurityControllerAdvice}.
 * 
 * @author Goed Bezig
 */
public class DefaultSecurityControllerAdviceTest {

    private DefaultSecurityControllerAdvice advice;

    private static final String VALID_JWT_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiJhZG1pbmlzdHJhdG9yIiwic3VybmFtZSI6IkdvZWQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXR0cmlidXRlcyI6eyJCU0VHIjoiTFIifSwiZXhwIjoxNTM4NDg1NTg4LCJmaXJzdF9uYW1lIjoiQWRtaW4iLCJhdXRob3JpdGllcyI6WyJEUF9JTlRFUk5BTF9BRE1JTiIsIkxTU09fQURNSU4iLCJEUF9FWFRFUk5BTF9BRE1JTiIsIkdPRURfU1VQRVJfQURNSU4iXSwianRpIjoiMTY0Njg2OTEtMDcxYi00NzAxLTlhM2YtOWUzNThhNzdhZTY1IiwiZW1haWwiOiJhZG1pbkBnb2VkLmNvbS5hdSIsImNsaWVudF9pZCI6ImdvZWRBcHAiLCJpYXQiOjE1Mzg0ODE5MzJ9.n5UefMWaKMAPjAjJnHX5SN6Zy3Mt4Z0pFplSt58EjzU";

    @Before
    public void setup() {
        this.advice = new DefaultSecurityControllerAdvice();
    }

    @Test
    public void shouldReturnAppropriateErrorResponseWhenAccessDenied() {
        SecurityErrorResponse response = advice.handleAccessDeniedException(new AccessDeniedException("Access Denied!"));
        assertEquals("Access Denied!", response.getReason());
        assertEquals(UNAUTHORIZED.name(), response.getCode());
    }

    @Test
    public void shouldGenerateUserModelWithValidJWTToken() {
        User user = advice.generateUserModel(VALID_JWT_TOKEN, null);
        assertEquals(user.getEmail(), "admin@goed.com.au");
        assertEquals(user.getFirstname(), "Admin");
        assertEquals(user.getSurname(), "Goed");
        assertEquals(user.getUsername(), "administrator");
        assertThat(user.getAuthorities(), containsInAnyOrder("DP_INTERNAL_ADMIN","LSSO_ADMIN","DP_EXTERNAL_ADMIN","GOED_SUPER_ADMIN"));
        assertThat(user.getAttributes(), hasEntry("BSEG", "LR"));
        assertThat(user.getScopes(), containsInAnyOrder("read", "write"));
    }
    
    @Test
    public void shouldGenerateAnonymousUserIfJWTTokenMissingBearerAndPrincipalNull() {
        User user = advice.generateUserModel(VALID_JWT_TOKEN.replaceAll("Bearer ", ""), null);
        assertEquals(user.getUsername(), User.ANONYMOUS_USER);
    }
    
    @Test
    public void shouldGeneratePrincipalUserIfJWTTokenMissingBearerAndPrincipalNotNull() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Lord Vader");
        User user = advice.generateUserModel(VALID_JWT_TOKEN.replaceAll("Bearer ", ""), principal);
        assertEquals(user.getUsername(), "Lord Vader");
    }
    
    @Test
    public void shouldGenerateUserModelWithNullJWTButWithPrincipal() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Lord Vader");
        User user = advice.generateUserModel(null, principal);
        assertEquals(user.getUsername(), "Lord Vader");
    }
    
    @Test
    public void shouldGenerateAnonymousModelWithNullJWTTokenAndNullPrincipal() {
        User user = advice.generateUserModel(null, null);
        assertEquals(user.getUsername(), User.ANONYMOUS_USER);
    }
}
