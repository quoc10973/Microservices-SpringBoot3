package com.quoc.microservices.order.config;

import com.quoc.microservices.order.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${inventory.service.url}") // Inject the inventory service URL from application.properties
    private String inventoryServiceUrl;

    @Bean
    public InventoryClient inventoryClient() {
        // Create a REST client to call the Inventory service
        RestClient restClient = RestClient.builder().
                baseUrl(inventoryServiceUrl)
                .requestFactory(getClientRequestFactory()) //
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient); // Create a REST client adapter for HttpServiceProxyFactory understand and use
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build(); // build the HttpServiceProxyFactory with the REST client adapter
        return httpServiceProxyFactory.createClient(InventoryClient.class); // Proxy is stand for interface InventoryClient and map it become HTTP request to call the Inventory service
    }

    private ClientHttpRequestFactory getClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(3).toMillis()); // Set the connection timeout to 3 seconds
        factory.setReadTimeout((int) Duration.ofSeconds(3).toMillis()); // Set the read timeout to 3 seconds
        return factory;
    }
}
