package com.purgeteam.swagger.starter.zuul;

import com.purgeteam.swagger.starter.SwaggerProperties;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * {@link SwaggerResourcesProvider} Swagger 服务列表获取
 *
 * @author purgeyao
 * @since 1.0
 */
@Primary
public class SwaggerDocumentationConfig implements SwaggerResourcesProvider,
        ApplicationListener<WebServerInitializedEvent> {

    private static final Logger log = LoggerFactory.getLogger(SwaggerDocumentationConfig.class);

    private static final String LOCATION = "v2/api-docs";

    private WebServerInitializedEvent event;

    private RestTemplate restTemplate;

    private final RouteLocator routeLocator;

    private SwaggerProperties swaggerProperties;

    public SwaggerDocumentationConfig(RouteLocator routeLocator, SwaggerProperties properties) {
        this.routeLocator = routeLocator;
        this.swaggerProperties = properties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<SwaggerResource> get() {

        List<Route> routes = routeLocator.getRoutes();

        // 是否排序
        if (swaggerProperties.getIsOptimizeLocation()) {
            Set<SwaggerRoute> swaggerRoutes = routes.stream().map(SwaggerRoute::new).collect(Collectors.toSet());
            List<SwaggerResource> swaggerResourceList = getSwaggerResourceList(swaggerRoutes);
            Collections.sort(swaggerResourceList);
            return swaggerResourceList;
        }

        return getSwaggerResourceList(new HashSet<>(routes));
    }

    private List<SwaggerResource> getSwaggerResourceList(Set<? extends Route> routes) {
        long startTime = System.currentTimeMillis();

        List<FutureTask<SwaggerResource>> futureTasks = routes.stream()
                .map(route -> new FutureTask<>(() -> apply(route)))
                .collect(Collectors.toList());
        List<Thread> threads = futureTasks.stream().map(Thread::new).collect(Collectors.toList());
        threads.forEach(Thread::start);

        List<SwaggerResource> swaggerResources = futureTasks.stream()
                .map(swaggerResourceFutureTask -> {
                    SwaggerResource swaggerResource = null;
                    try {
                        swaggerResource = swaggerResourceFutureTask.get();
                    } catch (Exception e) {
                        log.warn("线程执行异常");
                    }
                    return swaggerResource;
                }).collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        log.debug("所有服务获取 耗时为{}毫秒", (endTime - startTime));

        return swaggerResources;
    }

    private SwaggerResource apply(Route route) {
        long startTime = System.currentTimeMillis();
        String name = route.getId();
        try {
            int port = event.getWebServer().getPort();
            InetAddress address = InetAddress.getLocalHost();
            String url = "http://" + address.getHostAddress() + ":" + port;
            LinkedHashMap forObject = restTemplate
                    .getForObject(url + "/" + route.getLocation() + "/" + LOCATION, LinkedHashMap.class);
            if (forObject != null) {
                LinkedHashMap info = (LinkedHashMap) forObject.get("info");
                if (info != null) {
                    Object title = info.get("title");
                    if (title != null) {
                        name = "health > " + title + "(" + name + ")";
                    }
                }
            }
        } catch (Exception e) {
            log.warn("{} 服务api-docs获取失败.", route.getLocation());
            name = "sick > " + name + "(已下线)";
        }
        SwaggerResource swaggerResource = swaggerResource(name, route.getFullPath().replace("**", LOCATION),
                swaggerProperties.getVersion());
        long endTime = System.currentTimeMillis();
        log.debug("{}: {} 服务获取 耗时为{}毫秒.", Thread.currentThread().getId(),
                route.getLocation(), (endTime - startTime));

        return swaggerResource;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.event = event;
    }
}
