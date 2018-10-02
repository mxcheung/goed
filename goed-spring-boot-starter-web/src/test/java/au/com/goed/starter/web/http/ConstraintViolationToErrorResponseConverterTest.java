package au.com.goed.starter.web.http;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Unit Tests for {@link ConstratinViolationToErrorResponseConverterTest}.
 * 
 * @author Goed Bezig
 */
public class ConstraintViolationToErrorResponseConverterTest {

    private static final String MESSAGE_PROPERTIES_BASE = "messages";
    private static final String SIZE_CODE = "SIZE_CODE_TEST";

    private ConstraintViolationToErrorResponseConverter constraintViolationToErrorResponseConverter;
    private ErrorCodeMapper errorCodeMapper;
    private ResourceBundleMessageSource messages;
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Before
    public void setup() {
        this.messages = new ResourceBundleMessageSource();
        this.messages.setBasename(MESSAGE_PROPERTIES_BASE);
        this.errorCodeMapper = new ErrorCodeMapper(this.messages);

        this.constraintViolationToErrorResponseConverter = new ConstraintViolationToErrorResponseConverter(this.errorCodeMapper);

        this.validatorFactoryBean = new LocalValidatorFactoryBean();
        this.validatorFactoryBean.setValidationMessageSource(this.messages);
        this.validatorFactoryBean.afterPropertiesSet();
    }

    @Test
    public void shouldCreateErrorResponseFromViolation() throws Exception {
        TestModel model = new TestModel("123456");
        Set<ConstraintViolation<TestModel>> v1 = validatorFactoryBean.validate(model);

        ErrorResponse response = constraintViolationToErrorResponseConverter.apply(v1.iterator().next());

        assertEquals(SIZE_CODE, response.getCode());
        assertEquals("testString", response.getCause().getParameter());
        assertEquals(ErrorResponse.class.getName(), response.getClass().getName());

        Set<TestModel> models = new HashSet<>();
        models.add(new TestModel("123"));
        models.add(model);
        TestSet testSet = new TestSet(models);

        Set<ConstraintViolation<TestSet>> v2 = validatorFactoryBean.validate(testSet);

        response = constraintViolationToErrorResponseConverter.apply(v2.iterator().next());
        assertEquals(SIZE_CODE, response.getCode());
        assertEquals("testString", response.getCause().getParameter());
        assertEquals(ErrorResponse.class.getName(), response.getClass().getName());
    }

    private class TestModel {

        @Size(max = 5)
        private String testString;

        public TestModel(String testString) {
            this.testString = testString;
        }
    }

    private class TestSet {

        @Valid
        private Set<TestModel> models;

        public TestSet(Set<TestModel> models) {
            this.models = models;
        }
    }
}
