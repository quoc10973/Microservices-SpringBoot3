package com.quoc.microservices.order.repository;

import com.quoc.microservices.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
