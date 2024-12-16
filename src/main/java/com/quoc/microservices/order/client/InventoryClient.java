package com.quoc.microservices.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    // Call Inventory service to check if a product is in stock using GET method
    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod") // name should be the same as the name in application.properties
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    // Fallback method if the Inventory service is not available
    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable) {
        log.info("Can't not get response from Inventory service for skuCode: {} failure reason: {}", skuCode, throwable.getMessage());
        return false;
    }
}
