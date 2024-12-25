package com.quoc.microservices.order.service;

import com.quoc.microservices.order.client.InventoryClient;
import com.quoc.microservices.order.entity.Order;
import com.quoc.microservices.order.event.OrderPlacedEvent;
import com.quoc.microservices.order.model.OrderRequest;
import com.quoc.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate; // Inject KafkaTemplate, generic type is <String, OrderPlaceEvent>

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
        // Send message to Kafka topic
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(); // mvn clean compile to generate OrderPlacedEvent class
        orderPlacedEvent.setOrderNumber(order.getOrderNumber());
        orderPlacedEvent.setEmail(orderRequest.userDetail().email());
        orderPlacedEvent.setFirstName(orderRequest.userDetail().firstName());
        orderPlacedEvent.setLastName(orderRequest.userDetail().lastName());

        log.info("Start - Sending OrderPlaceEvent {} to Kafka topic order-placed", orderPlacedEvent); //log start

        kafkaTemplate.send("order-placed", orderPlacedEvent); // Send message to Kafka topic

        log.info("End - Sending OrderPlaceEvent {} to Kafka topic order-placed", orderPlacedEvent); //log end

        //now can go to Kafka UI to check if the message is sent at http://localhost:8086/
        // NOTE: If use Schema Registry by Avro, change the deserializer in application.properties to io.confluent.kafka.serializers.KafkaAvroDeserializer
    }
}
