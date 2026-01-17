package com.ecommerce.productsystem.controller;

import com.ecommerce.productsystem.dto.ProductDto;
import com.ecommerce.productsystem.model.Product;
import com.ecommerce.productsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/reduce-quantity/{id}")
    public ResponseEntity<Product> reduceQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(productService.reduceStock(id, quantity));
    }
}
