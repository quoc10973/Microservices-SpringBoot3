package com.quoc.microservices.inventory.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI inventoryServicesAPI() {
        return new OpenAPI()
                .info(new Info().title("Inventory Service API")
                        .description("REST API for Inventory Service")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Refer to Inventory Service Wiki Documentation")
                        .url("http://inventory-service-dummy-url.com/docs"));
    }
}
