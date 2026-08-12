package com.tanish.ecommerce.order_service.service;


import com.tanish.ecommerce.order_service.dto.OrderRequestDTO;
import com.tanish.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper orderMapper;

    public List<OrderRequestDTO> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll()
                .stream()
                .map(order -> orderMapper.map(order, OrderRequestDTO.class))
                .collect(Collectors.toList());
    }

    public OrderRequestDTO getOrderById(Long orderId) {
        log.info("Fetching order with ID: {}", orderId);
        return orderRepository.findById(orderId)
                .map(order -> orderMapper.map(order, OrderRequestDTO.class))
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }
}
