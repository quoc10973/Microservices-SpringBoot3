package com.quoc.microservices.product.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI productServicesAPI() {
        return new OpenAPI()
                .info(new Info().title("Product Service API")
                        .description("REST API for Product Service")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Refer to Product Service Wiki Documentation")
                        .url("http://product-service-dummy-url.com/docs"));
    }
}
