package com.payment.ordersystem.controller;

import com.payment.ordersystem.dto.request.OrderDto;
import com.payment.ordersystem.dto.response.OrderResponse;
import com.payment.ordersystem.model.Order;
import com.payment.ordersystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderDto request) {
        Order savedOrder = orderService.createOrder(request);
        return new ResponseEntity<>(mapToResponse(savedOrder), HttpStatus.CREATED);
    }

    @GetMapping("/all/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> responseList = orders.stream()
                .map(this::mapToResponse)
                .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(mapToResponse(order), HttpStatus.OK);
    }

    private OrderResponse mapToResponse(Order order) {
        List<com.payment.ordersystem.dto.response.ItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new com.payment.ordersystem.dto.response.ItemResponse(
                        item.getId(),
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice()))
                .toList();

        return new com.payment.ordersystem.dto.response.OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getTransactionId(),
                itemResponses);
    }

    @GetMapping("/order/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(@PathVariable String customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        List<OrderResponse> responseList = orders.stream()
                .map(this::mapToResponse)
                .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
