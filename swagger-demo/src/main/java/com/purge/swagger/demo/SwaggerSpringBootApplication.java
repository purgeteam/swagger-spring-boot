package com.purge.swagger.demo;

import io.purge.swagger.annotation.EnableSwaggerPlugins;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwaggerPlugins
@SpringBootApplication
public class SwaggerSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(SwaggerSpringBootApplication.class, args);
  }

}
