package com.purgeteam.swagger.starter.zuul;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * {@link SwaggerResourcesProvider} Swagger 服务列表获取
 *
 * @author purgeyao
 * @since 1.0
 */
@Primary
public class SwaggerDocumentationConfig implements SwaggerResourcesProvider {

  private final RouteLocator routeLocator;

  public SwaggerDocumentationConfig(RouteLocator routeLocator) {
    this.routeLocator = routeLocator;
  }

  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();
    List<Route> routes = routeLocator.getRoutes();
    routes.forEach(route ->
      resources.add(
          swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"), "1.0")
      )
    );
    return resources;
  }

  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
