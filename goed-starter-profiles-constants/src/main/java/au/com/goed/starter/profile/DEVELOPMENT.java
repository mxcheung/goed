package au.com.goed.starter.profile;

import static au.com.goed.starter.constant.Profiles.DEVELOPMENT;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Profile toggle extension to {@link Profile} for development active profiles.
 * 
 * @author Goed Bezig
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Profile(DEVELOPMENT)
public @interface DEVELOPMENT {

}
