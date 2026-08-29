package com.agententerprise.repository;

import com.agententerprise.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductCodeContainingIgnoreCaseOrWarehouseCodeContainingIgnoreCase(String productCode, String warehouseCode);
}
