package au.com.goed.starter.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidStringValidator implements ConstraintValidator<ValidString, String> {

    @Override
    public void initialize(ValidString arg0) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value != null && value.indexOf("what") >= 0) {
            return false;
        }

        return true;
    }

}
