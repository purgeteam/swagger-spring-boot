package io.purge.swagger;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author purgeyao
 * @since 0.1.0
 */
@Data
@ToString
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {

  public static final String PREFIX = "swagger";

  /**
   * 文档扫描包路径
   */
  private String basePackage = "";

  /**
   * title 如: 用户模块系统接口详情
   */
  private String title = "深兰云平台系统接口详情";

  /**
   * 服务文件介绍
   */
  private String description = "在线文档";

  /**
   * 服务条款网址
   */
  private String termsOfServiceUrl = "https://www.deepblueai.com/";

  /**
   * 版本
   */
  private String version = "V1.0";


}
