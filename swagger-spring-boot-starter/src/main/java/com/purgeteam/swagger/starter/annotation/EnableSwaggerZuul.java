package com.purgeteam.swagger.starter.annotation;

import com.purgeteam.swagger.starter.zuul.SwaggerDocumentationConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * zuul 集成 swagger
 *
 * @author purgeyao
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SwaggerDocumentationConfig.class)
@EnableSwaggerPlugins
public @interface EnableSwaggerZuul {

}
