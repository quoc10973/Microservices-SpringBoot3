package com.quoc.microservices.product.model;
import java.math.BigDecimal;

//record automatically generates the constructor, getter, equals, hashCode, and toString methods
public record ProductRequest(String id, String name, String description, BigDecimal price) {
}
