package com.agententerprise.service;

import com.agententerprise.domain.Inventory;
import com.agententerprise.domain.Product;
import com.agententerprise.dto.DashboardSummary;
import com.agententerprise.repository.InventoryRepository;
import com.agententerprise.repository.ProductRepository;
import com.agententerprise.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final SalesOrderRepository orderRepository;

    public DashboardService(ProductRepository productRepository, InventoryRepository inventoryRepository,
                            SalesOrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
    }

    public DashboardSummary summary() {
        Map<String, Product> products = productRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getCode, Function.identity(), (left, right) -> left));
        long lowStock = inventoryRepository.findAll().stream()
                .filter(item -> isLowStock(item, products))
                .count();
        return new DashboardSummary(productRepository.count(), inventoryRepository.count(), orderRepository.count(), lowStock);
    }

    private boolean isLowStock(Inventory item, Map<String, Product> products) {
        Product product = products.get(item.getProductCode());
        return product != null && item.getQuantity() - item.getReservedQuantity() < product.getSafetyStock();
    }
}
