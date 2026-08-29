package com.agententerprise.config;

import com.agententerprise.domain.*;
import com.agententerprise.repository.InventoryRepository;
import com.agententerprise.repository.ProductRepository;
import com.agententerprise.repository.SalesOrderRepository;
import com.agententerprise.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedDemoData(UserAccountRepository users, ProductRepository products,
                                          InventoryRepository inventory, SalesOrderRepository orders) {
        return args -> {
            if (users.count() == 0) {
                users.save(new UserAccount("sales-demo", "销售演示员", RoleType.SALES));
                users.save(new UserAccount("procurement-demo", "采购演示员", RoleType.PROCUREMENT));
                users.save(new UserAccount("production-demo", "生产演示员", RoleType.PRODUCTION));
                users.save(new UserAccount("warehouse-demo", "仓储演示员", RoleType.WAREHOUSE));
            }
            if (products.count() == 0) {
                products.save(new Product("P-1001", "智能终端X", "台", new BigDecimal("680.00"), 100));
                products.save(new Product("P-1002", "工业传感器Y", "个", new BigDecimal("120.00"), 200));
                products.save(new Product("M-2001", "控制模块", "件", new BigDecimal("85.00"), 300));
            }
            if (inventory.count() == 0) {
                inventory.save(new Inventory("P-1001", "WH-SH-01", 680, 120));
                inventory.save(new Inventory("P-1002", "WH-SH-01", 420, 80));
                inventory.save(new Inventory("M-2001", "WH-SH-01", 180, 60));
            }
            if (orders.count() == 0) {
                orders.save(new SalesOrder("SO-202608-001", "华东制造客户", "P-1001", 1000,
                        LocalDate.now().plusDays(10), "待交付评估", LocalDateTime.now().minusHours(3)));
                orders.save(new SalesOrder("SO-202608-002", "北方集成客户", "P-1002", 120,
                        LocalDate.now().plusDays(18), "生产中", LocalDateTime.now().minusDays(1)));
            }
        };
    }
}
