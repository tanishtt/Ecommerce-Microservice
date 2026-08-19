package com.tanish.ecommerce.order_service.controller;


import com.tanish.ecommerce.order_service.dto.OrderRequestDTO;
import com.tanish.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
@RefreshScope
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

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO, @RequestHeader("X-User-ID") Long userId){
        log.info("ORDER : userid: {}", userId);
        OrderRequestDTO orderRequestDTO1 = orderService.createOrder(orderRequestDTO);

        return ResponseEntity.ok(orderRequestDTO1);
    }
}
