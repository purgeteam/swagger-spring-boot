# swagger-spring-boot

[![Maven Central](https://img.shields.io/maven-central/v/com.purgeteam/swagger-spring-boot-starter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.purgeteam%20AND%20a:swagger-spring-boot-starter)
![License](https://img.shields.io/badge/SpringBoot-2.1.7RELEASE-green.svg)
![License](https://img.shields.io/badge/JAVA-1.8+-green.svg)
![License](https://img.shields.io/badge/maven-3.0+-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

qq交流群:`812321371`

## 简介:
- 集成`swagger`前端接口文档

## 使用说明:

> 添加依赖

**ps:** 实际version版本请使用最新版
**最新版本:** [![Maven Central](https://img.shields.io/maven-central/v/com.purgeteam/swagger-spring-boot-starter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.purgeteam%20AND%20a:swagger-spring-boot-starter)

```
<dependency>
  <groupId>com.purgeteam</groupId>
  <artifactId>swagger-spring-boot-starter</artifactId>
  <version>0.1.0.RELEASE</version>
</dependency>
```

### 1. @EnableSwaggerPlugins特性
> 配置swagger.properties文件
- 创建 `swagger.properties` 配置文件
        到自己模块的`resources`目录下 更改`swagger.properties`配置

- swagger.properties 介绍

```properties
swagger.basePackage="swagger扫描项目包路径"
swagger.title="swagger网页显示标题"
swagger.description="swagger网页显示介绍"
```

> 启动类添加`@EnableSwaggerPlugins`注解。

```
@EnableSwaggerPlugins
@SpringBootApplication(scanBasePackages = {"io.deepblueai.frontdemo.service"})
public class FrontDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(FrontDemoApplication.class, args);
  }

}
```
访问`http://ip:端口/swagger-ui.html`检查swagger-ui是否正常。
