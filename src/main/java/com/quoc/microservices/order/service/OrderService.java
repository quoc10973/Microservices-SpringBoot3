package com.quoc.microservices.order.service;

import com.quoc.microservices.order.client.InventoryClient;
import com.quoc.microservices.order.entity.Order;
import com.quoc.microservices.order.model.OrderRequest;
import com.quoc.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        // Check if product is in stock
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if(!isProductInStock) {
            throw new RuntimeException("Product with skuCode: " + orderRequest.skuCode() + " is out of stock");
        }
        // Create order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        // Save order
        orderRepository.save(order);
    }
}
