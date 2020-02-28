package com.purgeteam.swagger.starter.knife4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
public class Knife4jConfiguration {

  @Bean
  public DocketPostProcessor docketPostProcessor() {
    return new DocketPostProcessor();
  }
}
