package com.tanish.ecommerce.order_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private Long id;
    private List<OrderRequestItemDTO> items;
    private Double totalPrice;
}
