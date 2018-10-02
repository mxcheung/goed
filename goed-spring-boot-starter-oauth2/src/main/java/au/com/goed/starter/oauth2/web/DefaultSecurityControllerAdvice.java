package au.com.goed.starter.oauth2.web;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import au.com.goed.starter.oauth2.model.User;

/**
 * Default rest controller advice for handling Security Exceptions and decoding JWT token into a user object.
 * 
 * @author Goed Bezig
 */
@RestControllerAdvice
@Order(LOWEST_PRECEDENCE - 1)
public class DefaultSecurityControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSecurityControllerAdvice.class);

    private static final String HEADER_AUTHORIZATION = "Authorization";

    private static final String JWT_BEARER = "Bearer";

    /**
     * Handles {@link AccessDeniedException} that gets thrown by @PreAuthorize and @PostAuthorize annotations.
     * 
     * @param ade the exception.
     * @return {@link SecurityErrorResponse}.
     */
    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    SecurityErrorResponse handleAccessDeniedException(Exception ade) {
        LOGGER.info("Access Denied Exception being handled: {}", ade.getMessage());
        return new SecurityErrorResponse(UNAUTHORIZED.name(), ade.getMessage());
    }

    /**
     * Checks if an Authorization header exists, if so decodes the JWT that should be set. Otherwise will
     * attempt to populate some information from the given {@link Principal}, else will create an Empty Anonymous user.
     * 
     * @param token JWT token appended to Authorization header.
     * @param principal see {@link Principal}.
     * @return {@link User}.
     */
    @ModelAttribute("user")
    User generateUserModel(@RequestHeader(value = HEADER_AUTHORIZATION, required = false) final String token, final Principal principal) {
        User user = null;
        if (token != null && token.trim().startsWith(JWT_BEARER)) {
            String cleanToken = token.trim().replace(JWT_BEARER, "").trim();
            Jwt jwt = JwtHelper.decode(cleanToken);
            user = User.fromJson(jwt.getClaims());
        } else if (principal != null) {
            user = User.fromPrincipal(principal);
        } else {
            user = User.anonymousUser();
        }
        return user;
    }

}
