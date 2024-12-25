package com.quoc.microservices.order.controller;

import com.quoc.microservices.order.model.OrderRequest;
import com.quoc.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        // create order
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }
}
