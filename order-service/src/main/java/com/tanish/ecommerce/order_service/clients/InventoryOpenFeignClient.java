package com.tanish.ecommerce.order_service.clients;

import com.tanish.ecommerce.order_service.dto.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "inventory-service", path = "/inventory")
public interface InventoryOpenFeignClient {

    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(OrderRequestDTO orderRequestDTO);
}
