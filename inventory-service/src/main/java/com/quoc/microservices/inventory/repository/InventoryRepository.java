package com.quoc.microservices.inventory.repository;

import com.quoc.microservices.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Find the inventory by skuCode where quantity >= 0 and return true if found
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);
}
