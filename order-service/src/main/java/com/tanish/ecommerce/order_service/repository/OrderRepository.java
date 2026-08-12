package com.tanish.ecommerce.order_service.repository;

import com.tanish.ecommerce.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
