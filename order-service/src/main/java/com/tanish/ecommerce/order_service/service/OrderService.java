package com.tanish.ecommerce.order_service.service;


import com.tanish.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.tanish.ecommerce.order_service.dto.OrderRequestDTO;
import com.tanish.ecommerce.order_service.entity.OrderStatusEnum;
import com.tanish.ecommerce.order_service.entity.Orders;
import com.tanish.ecommerce.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
    private final InventoryOpenFeignClient inventoryOpenFeignClient;


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

//    @Retry(name = "inventoryRetry", fallbackMethod = "fallbackCreateOrder")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "fallbackCreateOrder")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "fallbackCreateOrder")
    public OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // Call the inventory service to reduce stocks
        Double totalCost = inventoryOpenFeignClient.reduceStocks(orderRequestDTO);
        // Set the total cost in the order request DTO
        orderRequestDTO.setTotalPrice(totalCost);
        // Save the order to the database
        Orders order = orderMapper.map(orderRequestDTO, Orders.class);
        for (var item : order.getItems()) {
            item.setOrder(order);
        }

        order.setTotalPrice(totalCost);
        order.setStatus(OrderStatusEnum.CONFIRMED);
        Orders savedOrder = orderRepository.save(order);
        // Return the created order
        return orderMapper.map(savedOrder, OrderRequestDTO.class);
    }

    public OrderRequestDTO fallbackCreateOrder(OrderRequestDTO orderRequestDTO, Throwable throwable) {
        log.error("Failed to create order due to inventory service unavailability: {}", throwable.getMessage());
        throw new RuntimeException("Inventory service is currently unavailable. Please try again later.");
    }
}
