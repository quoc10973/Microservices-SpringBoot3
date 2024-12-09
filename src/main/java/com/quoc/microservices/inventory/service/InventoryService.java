package com.quoc.microservices.inventory.service;

import com.quoc.microservices.inventory.entity.Inventory;
import com.quoc.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        // Find the inventory by skuCode where quantity >= 0 and return true if found
       return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

}
