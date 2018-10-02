package au.com.goed.starter.web.configuration;

import static org.springframework.boot.autoconfigure.condition.SearchStrategy.CURRENT;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import au.com.goed.starter.web.DefaultErrorController;
import au.com.goed.starter.web.DefaultExceptionControllerAdvice;
import au.com.goed.starter.web.VersionEndpoint;
import au.com.goed.starter.web.filter.RequestLoggingFilter;
import au.com.goed.starter.web.http.ConstraintViolationToErrorResponseConverter;
import au.com.goed.starter.web.http.ErrorCodeMapper;
import au.com.goed.starter.web.http.ErrorResponsesCreator;

/**
 * goed Starter default web configuration, of which can be overridden by providing an alternate
 * configuration within your own service.
 * 
 * @author Goed Bezig
 */
@Configuration
@PropertySource({ "classpath:webDefaults.properties" })
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@ConditionalOnWebApplication
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class WebConfiguration {

    /**
     * Prepare a non Ribbon Aware Rest Template if running in a profile that is not "cloud" enbabled.
     * See {@link CloudConfiguration} for creation of cloud enabled rest template.
     * 
     * @return Non Cloud Aware Rest Template.
     */
    @ConditionalOnMissingBean(value = RestTemplate.class)
    @Bean
    @Qualifier("sdRestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    
    
    /**
     * Enable version actuator endpoint. Available at /version.
     * 
     * @param environment the current application environment.
     * @return {@link VersionEndpoint}.
     */
    @Bean
    public VersionEndpoint version(Environment environment) {
        return new VersionEndpoint(environment);
    }

    /**
     * Prepare the {@link ErrorResponsesCreator} that creates error responses in a standardized format.
     * 
     * @param errorCodeMapper error code mapping.
     * @param constraintViolationToErrorResponseConverter handle conversion of constraint violations.
     * @return standard error response.
     */
    @Bean
    public ErrorResponsesCreator errorResponsesCreator(ErrorCodeMapper errorCodeMapper,
            ConstraintViolationToErrorResponseConverter constraintViolationToErrorResponseConverter) {
        return new ErrorResponsesCreator(errorCodeMapper, constraintViolationToErrorResponseConverter);
    }

    @Bean
    public ErrorCodeMapper errorCodeMapper(MessageSource messageSource) {
        return new ErrorCodeMapper(messageSource);
    }

    @Bean
    public ConstraintViolationToErrorResponseConverter constraintViolationToErrorResponseConverter(ErrorCodeMapper errorCodeMapper) {
        return new ConstraintViolationToErrorResponseConverter(errorCodeMapper);
    }

    /**
     * Configure the use of the {@link DefaultExceptionControllerAdvice} controller advice to handle
     * exceptions that have slipped through the application, in an attempt to better resolve them beyond the
     * spring defaults.
     * 
     * @param errorResponsesCreator creates the error responses as part of an error payload.
     * @param messageSource message resolver.
     * @return Configured exception controller advice.
     */
    @Bean
    public DefaultExceptionControllerAdvice defaultExceptionHandler(ErrorResponsesCreator errorResponsesCreator, MessageSource messageSource) {
        return new DefaultExceptionControllerAdvice(errorResponsesCreator, messageSource);
    }

    /**
     * Configure the use of a custom error controller to supersede the white label error handler configured by
     * spring.
     * 
     * @param errorAttributes accessible error attributes.
     * @return Configured white label error handler.
     */
    @Bean
    @ConditionalOnMissingBean(value = ErrorController.class, search = CURRENT)
    public DefaultErrorController defaultErrorController(ErrorAttributes errorAttributes) {
        return new DefaultErrorController(errorAttributes);
    }

    /**
     * Default BeanPostProcessor implementation that delegates to a JSR-303 (bean validation) provider for
     * performing method-level validation on annotated methods.
     * 
     * @return {@link MethodValidationPostProcessor}.
     */
    @Bean
    @ConditionalOnMissingBean(value = MethodValidationPostProcessor.class, search = CURRENT)
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * Configuration of pre and post request logging filter.
     * 
     * @return Configured {@link RequestLoggingFilter}.
     */
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        RequestLoggingFilter rlf = new RequestLoggingFilter();
        rlf.setIncludeQueryString(true);
        rlf.setIncludeHeaders(true);
        rlf.setIncludePayload(true);
        rlf.setIncludeClientInfo(true);
        return rlf;
    }

    @SuppressWarnings("rawtypes")
    @Configuration
    public static class WebConfig extends WebMvcConfigurerAdapter {

        private static final String DEFAULT_ENCODING = "UTF-8";

        // Default to LocalValidatorFactoryBean if not overridden by configuration.
        @Value("${goed.validation.class:org.springframework.validation.beanvalidation.LocalValidatorFactoryBean}")
        private String validator;

        // Comma separated list of message properties to use as validation message source.
        @Value("${goed.validation.messages.basenames:messages,goedMessagesDefaults}")
        private String basenames;

        @Override
        @ConditionalOnMissingBean(value = SpringValidatorAdapter.class, search = CURRENT)
        public LocalValidatorFactoryBean getValidator() {
            LocalValidatorFactoryBean localValidatorFactoryBean;

            try {
                Class c = Class.forName(validator);
                localValidatorFactoryBean = (LocalValidatorFactoryBean) c.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                localValidatorFactoryBean = new LocalValidatorFactoryBean();
            }

            localValidatorFactoryBean.setValidationMessageSource(getMessageSource());
            return localValidatorFactoryBean;
        }

        @Bean(name = "messageSource")
        @ConditionalOnMissingBean(value = AbstractMessageSource.class, search = CURRENT)
        public ResourceBundleMessageSource getMessageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasenames(basenames.split(","));
            messageSource.setDefaultEncoding(DEFAULT_ENCODING);
            return messageSource;
        }
    }
}
