package com.agententerprise.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String code;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 16)
    private String unit;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal standardCost = BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer safetyStock = 0;

    public Product() {
    }

    public Product(String code, String name, String unit, BigDecimal standardCost, Integer safetyStock) {
        this.code = code;
        this.name = name;
        this.unit = unit;
        this.standardCost = standardCost;
        this.safetyStock = safetyStock;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public BigDecimal getStandardCost() { return standardCost; }
    public void setStandardCost(BigDecimal standardCost) { this.standardCost = standardCost; }
    public Integer getSafetyStock() { return safetyStock; }
    public void setSafetyStock(Integer safetyStock) { this.safetyStock = safetyStock; }
}
