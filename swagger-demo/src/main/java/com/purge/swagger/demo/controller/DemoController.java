package com.purge.swagger.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author purgeyao
 * @since 1.0
 */
@RestController
public class DemoController {

  @Value("${test:demo}")
  private String test;

  @Value("${list}")
  private List<String> testlist;

  @GetMapping("getTest")
  public String test(){
    return test;
  }

  @GetMapping("testlist")
  public List<String> testlist(){
    return testlist;
  }

}
