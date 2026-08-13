package com.tanish.ecommerce.inventory_service.controller;


import com.tanish.ecommerce.inventory_service.dto.ProductDTO;
import com.tanish.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllInventory() {
        log.info("Received request to fetch all products");
        List<ProductDTO> products = productService.getAllInventory();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        log.info("Received request to fetch product with ID: {}", productId);
        ProductDTO product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}
