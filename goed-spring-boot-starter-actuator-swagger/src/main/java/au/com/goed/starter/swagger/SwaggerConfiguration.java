package au.com.goed.starter.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import au.com.goed.starter.profile.DEVELOPMENT;
import au.com.goed.starter.profile.LOCAL;
import au.com.goed.starter.profile.UAT;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;

/**
 * <p>
 * Creating a basic Swagger Configuration using SpringFox implementation of the Swagger2 specification.
 * </p>
 * <p>
 * Swagger is a framework API which allows you to describe the structure of your API's so that machines can
 * read them. The beauty of this is the ability to create beautiful and interactive API documentation.
 * </p>
 * <p>
 * See the following article on a good description on setting up Swagger with a Spring REST API.
 * http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * </p>
 * @author Goed Bezig
 */
@LOCAL
@UAT
@DEVELOPMENT
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${api.title:}")
    private String apiTitle;

    @Value("${api.description:}")
    private String apiDescription;

    @Value("${api.terms.of.service:}")
    private String apiTermsOfService;

    @Value("${api.license:}")
    private String apiLicense;

    @Value("${api.license.url:}")
    private String apiLicenseUrl;

    @Value("${revision:1.0.0}${changelist:-SNAPSHOT}")
    private String apiVersion;

    /**
     * Creation of the Swagger Docket Bean.
     * Note: forces to pick up endpoints configured with the au.com.goed base package. 
     * @return Swagger {@link Docket}.
     */
    @Bean(name = "SwaggerDocket")
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("au.com.goed"))
                .paths(any())
                .build();
    }

    /**
     * Ability to customize API documentation via properties file.
     * @return Swagger {@link ApiInfo}.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiTitle)
                .description(apiDescription)
                .termsOfServiceUrl(apiTermsOfService)
                .license(apiLicense)
                .licenseUrl(apiLicenseUrl)
                .version(apiVersion)
                .build();
    }
}
