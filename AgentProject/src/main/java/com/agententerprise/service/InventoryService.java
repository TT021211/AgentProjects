package com.agententerprise.service;

import com.agententerprise.domain.Inventory;
import com.agententerprise.domain.Product;
import com.agententerprise.dto.InventoryView;
import com.agententerprise.repository.InventoryRepository;
import com.agententerprise.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public List<InventoryView> search(String keyword) {
        List<Inventory> inventory = StringUtils.hasText(keyword)
                ? inventoryRepository.findByProductCodeContainingIgnoreCaseOrWarehouseCodeContainingIgnoreCase(keyword, keyword)
                : inventoryRepository.findAll();
        Map<String, Product> products = productRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getCode, Function.identity(), (left, right) -> left));
        List<InventoryView> result = new ArrayList<>();
        for (Inventory item : inventory) {
            Product product = products.get(item.getProductCode());
            result.add(new InventoryView(item.getProductCode(), product == null ? "未知产品" : product.getName(),
                    item.getWarehouseCode(), item.getQuantity(), item.getReservedQuantity()));
        }
        return result;
    }
}
