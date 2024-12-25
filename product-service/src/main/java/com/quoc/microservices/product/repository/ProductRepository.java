package com.quoc.microservices.product.repository;

import com.quoc.microservices.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product, String> {
}
