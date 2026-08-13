package com.tanish.ecommerce.inventory_service.service;


import com.tanish.ecommerce.inventory_service.client.OrdersFeignClient;
import com.tanish.ecommerce.inventory_service.dto.ProductDTO;
import com.tanish.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final OrdersFeignClient ordersFeignClient;

    public List<ProductDTO> getAllInventory() {
        log.info("Fetching all products from inventory");
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    public ProductDTO getProductById(Long productId) {
        log.info("Fetching product with ID: {}", productId);
        return productRepository.findById(productId)
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }
}
