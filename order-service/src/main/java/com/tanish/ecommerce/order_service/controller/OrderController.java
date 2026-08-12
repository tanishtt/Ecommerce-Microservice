package com.tanish.ecommerce.order_service.controller;


import com.tanish.ecommerce.order_service.dto.OrderRequestDTO;
import com.tanish.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders(){
        log.info("Received request to fetch all orders");
        List<OrderRequestDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderRequestDTO> getOrderById(@PathVariable Long orderId){
        log.info("Received request to fetch order with ID: {}", orderId);
        OrderRequestDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
}
