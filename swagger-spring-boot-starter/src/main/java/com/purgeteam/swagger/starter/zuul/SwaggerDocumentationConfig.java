package com.purgeteam.swagger.starter.zuul;

import com.purgeteam.swagger.starter.SwaggerProperties;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

  private static final String LOCATION = "v2/api-docs";

  private final RouteLocator routeLocator;

  private SwaggerProperties swaggerProperties;

  public SwaggerDocumentationConfig(RouteLocator routeLocator,
      SwaggerProperties swaggerProperties) {
    this.routeLocator = routeLocator;
    this.swaggerProperties = swaggerProperties;
  }
  @Override
  public List<SwaggerResource> get() {
    List<Route> routes = routeLocator.getRoutes();

    if (swaggerProperties.getIsOptimizeLocation()) {
      Set<SwaggerRoute> swaggerRoutes = routes.stream().map(SwaggerRoute::new)
          .collect(Collectors.toSet());
      return swaggerRoutes.stream().map(this::apply).sorted().collect(Collectors.toList());
    }

    return routes.stream().map(this::apply).sorted().collect(Collectors.toList());
  }

  private SwaggerResource apply(Route route) {
    return swaggerResource(route.getId(), route.getFullPath().replace("**", LOCATION),
        swaggerProperties.getVersion());
  }

  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
