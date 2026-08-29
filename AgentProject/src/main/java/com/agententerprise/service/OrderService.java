package com.agententerprise.service;

import com.agententerprise.domain.SalesOrder;
import com.agententerprise.dto.OrderView;
import com.agententerprise.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final SalesOrderRepository orderRepository;

    public OrderService(SalesOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderView> latest() {
        return orderRepository.findTop20ByOrderByCreatedAtDesc().stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }

    public List<SalesOrder> rawLatest() {
        return orderRepository.findTop20ByOrderByCreatedAtDesc();
    }

    private OrderView toView(SalesOrder order) {
        return new OrderView(order.getOrderNo(), order.getCustomerName(), order.getProductCode(),
                order.getQuantity(), order.getRequiredDate(), order.getStatus(), order.getCreatedAt());
    }
}
