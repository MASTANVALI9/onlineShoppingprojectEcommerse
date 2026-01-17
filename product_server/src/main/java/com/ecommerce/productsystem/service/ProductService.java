package com.ecommerce.productsystem.service;

import com.ecommerce.productsystem.dto.ProductDto;
import com.ecommerce.productsystem.model.Product;
import com.ecommerce.productsystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    /**
     * Reduces the stock of a product.
     * This method is designed to be called by the Order Service.
     * It uses @Transactional to ensure data consistency.
     */
    @Transactional
    public Product reduceStock(Long id, Integer quantity) {
        Product product = getProductById(id);

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        product.setQuantity(product.getQuantity() - quantity);
        return productRepository.save(product);
    }
}
