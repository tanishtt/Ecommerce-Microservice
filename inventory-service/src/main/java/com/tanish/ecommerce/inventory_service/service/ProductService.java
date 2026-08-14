package com.tanish.ecommerce.inventory_service.service;


import com.tanish.ecommerce.inventory_service.client.OrdersFeignClient;
import com.tanish.ecommerce.inventory_service.dto.OrderRequestDTO;
import com.tanish.ecommerce.inventory_service.dto.ProductDTO;
import com.tanish.ecommerce.inventory_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Double reduceStocks(OrderRequestDTO orderRequestDTO) {
        log.info("Reducing stocks for order: {}", orderRequestDTO);
        double totalCost = 0.0;

        for (var item : orderRequestDTO.getItems()) {
            var product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + item.getProductId()));

            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product ID: " + item.getProductId());
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            totalCost += product.getPrice() * item.getQuantity();
        }

        log.info("Total cost for the order: {}", totalCost);
        return totalCost;
    }
}
