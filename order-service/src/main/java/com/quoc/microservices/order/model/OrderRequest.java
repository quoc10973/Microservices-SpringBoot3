package com.quoc.microservices.order.model;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity, UserDetail userDetail) {

    public record UserDetail(String email, String firstName, String lastName) {}
}
