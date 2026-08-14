package com.tanish.ecommerce.inventory_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    List<OrderRequestItemDTO> items;
}
