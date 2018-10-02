package au.com.goed.starter.web;

import static org.springframework.http.HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for test application used in integration testing.
 * 
 * @author Goed Bezig
 */
@RestController
@RequestMapping("/integration")
@Validated
public class AppController {

    @InitBinder
    void initBinder(final WebDataBinder binder) {
        binder.addValidators(new AppValidator());
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public AppModel createPost(@RequestBody @Valid AppModel model) {
        return model;
    }

    @GetMapping(params = { "message" })
    public AppModel runtimeException(@RequestParam(required = true) String message) {
        throw new RuntimeException(message);
    }

    @GetMapping
    public AppModel appException() {
        throw new AppException("Bandwidth Exceeded!");
    }

    @GetMapping(params = { "str", "age" })
    public AppModel createViaParams(@RequestParam(required = true) String str, @RequestParam(required = true) Integer age) {
        return new AppModel(str, age);
    }

    @GetMapping(params = { "badstr" })
    public AppModel createViaParams(@RequestParam(required = true) String badstr) {
        AppModel model = new AppModel(badstr + "12345678901", 18);
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.afterPropertiesSet();
        Set<ConstraintViolation<AppModel>> validation = factory.validate(model);
        throw new ConstraintViolationException(validation);
    }

    private class AppValidator implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return AppModel.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            AppModel model = (AppModel) target;
            if (!isEmpty(model.getStr()) && !model.getStr().startsWith("test")) {
                errors.reject("test.reject.message");
            }

        }

    }

    @ResponseStatus(value = BANDWIDTH_LIMIT_EXCEEDED)
    private class AppException extends RuntimeException {

        private static final long serialVersionUID = 9176303597823878989L;

        public AppException(String message) {
            super(message);
        }
    }
}
