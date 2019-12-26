package com.purgeteam.swagger.starter.zuul;

import java.util.Set;

import org.springframework.cloud.netflix.zuul.filters.Route;

/**
 * @author purgeyao
 * @since 1.0
 */
public class SwaggerRoute extends Route {

    public SwaggerRoute(Route route) {
        super(route.getId(), route.getPath(), route.getLocation(), route.getPrefix(),
                route.getRetryable(), route.getSensitiveHeaders());
    }

    public SwaggerRoute(String id, String path, String location, String prefix, Boolean retryable,
                        Set<String> ignoredHeaders) {
        super(id, path, location, prefix, retryable, ignoredHeaders);
    }

    @Override
    public boolean equals(Object obj) {
        SwaggerRoute swaggerRoute = (SwaggerRoute) obj;
        return super.getLocation().equals(swaggerRoute.getLocation());
    }

    @Override
    public int hashCode() {
        String in = super.getLocation();
        return in.hashCode();
    }

}
