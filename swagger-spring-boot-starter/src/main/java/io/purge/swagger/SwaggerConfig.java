package io.purge.swagger;

import javax.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author purgeyao
 * @since 0.1.0
 */
@Configuration
@PropertySource(value = "classpath:swagger.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

  @Resource
  private SwaggerProperties swaggerProperties;

  @Bean
  public Docket buildDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(buildApiInf())
        .select()
        .apis(RequestHandlerSelectors.basePackage(""))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo buildApiInf() {
    return new ApiInfoBuilder()
        .title(swaggerProperties.getTitle())
        .description(swaggerProperties.getDescription())
        .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
        .contact(new Contact("skyworth", swaggerProperties.getTermsOfServiceUrl(), ""))
        .version(swaggerProperties.getVersion())
        .build();
  }
}
