package com.agententerprise.repository;

import com.agententerprise.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
    List<Product> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String code, String name);
}
