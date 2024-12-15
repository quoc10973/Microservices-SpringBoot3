package com.quoc.microservices.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {

    @Bean
    //Function endpoint for product service
    public RouterFunction<ServerResponse> productServiceRoute() {
        //if the request is /api/product, forward it to http://localhost:8080
        return GatewayRouterFunctions
                .route("product_service")
                .route(RequestPredicates.path("/api/products"), HandlerFunctions.http("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
        return GatewayRouterFunctions
                .route("product_service_swagger")
                .route(RequestPredicates.path("/aggregate/products-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8080"))
                .filter(setPath("/v3/api-docs"))
                .build();
        // filter(setPath("api-docs")) set the url path http://localhost:8080/aggregate/product-service/v3/api-docs to http://localhost:8080/v3/api-docs
        // this must be the same as the api doc path in the OpenAPIConfig.java file in the product service: ** springdoc.api-docs.path=/v3/api-docs **
    }

    @Bean
    //Function endpoint for order service
    public RouterFunction<ServerResponse> orderServiceRoute() {
        //if the request is /api/orders, forward it to http://localhost:8081
        return GatewayRouterFunctions
                .route("orders_service")
                .route(RequestPredicates.path("/api/orders"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
        return GatewayRouterFunctions
                .route("orders_service_swagger")
                .route(RequestPredicates.path("/aggregate/orders-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    //Function endpoint for inventory service
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        //if the request is /api/inventory, forward it to http://localhost:8082
        return GatewayRouterFunctions
                .route("inventory_service")
                .route(RequestPredicates.path("/api/inventory"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
        return GatewayRouterFunctions
                .route("inventory_service_swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}
