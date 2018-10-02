package au.com.goed.starter.oauth2.profile;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Feature toggle extension to {@link Profile} to enable Oauth2 JDBC Token Authorization.
 * 
 * @author Goed Bezig
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Profile("jdbcToken")
public @interface JDBCToken {

}
