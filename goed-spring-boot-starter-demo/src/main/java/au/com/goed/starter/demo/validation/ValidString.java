package au.com.goed.starter.demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {ValidStringValidator.class})
@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidString {

    String message() default "{validation.string.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
