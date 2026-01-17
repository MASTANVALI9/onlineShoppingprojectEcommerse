package com.payment.ordersystem.service;

import com.payment.ordersystem.dto.request.OrderDto;
import com.payment.ordersystem.dto.request.OrderItemDto;
import com.payment.ordersystem.model.Item;
import com.payment.ordersystem.model.Order;
import com.payment.ordersystem.model.OrderItem;
import com.payment.ordersystem.repository.ItemRepository;
import com.payment.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerId(orderDto.getCustomerId());
        order.setStatus("PENDING"); // Initial status

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        if (orderDto.getItems() != null) {
            for (OrderItemDto itemDto : orderDto.getItems()) {
                // 1. Validate Product Exists
                Item product = itemRepository.findById(itemDto.getProductId())
                        .orElseThrow(
                                () -> new RuntimeException("Product not found with ID: " + itemDto.getProductId()));

                // 2. Check Inventory (Synchronous Check)
                if (product.getQuantity() < itemDto.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product identifier: " + product.getName());
                }

                // 3. Update Inventory
                product.setQuantity(product.getQuantity() - itemDto.getQuantity());
                itemRepository.save(product);

                OrderItem item = new OrderItem();
                item.setProductId(product.getId());
                item.setProductName(product.getName()); // Use Name from DB (Single Source of Truth)
                item.setQuantity(itemDto.getQuantity());
                item.setPrice(product.getPrice()); // Use Price from DB (Secure)
                item.setOrder(order);

                orderItems.add(item);
                totalAmount += item.getPrice() * item.getQuantity();
            }
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        // Payment Integration
        com.payment.ordersystem.dto.response.PaymentResponse paymentResponse = paymentService
                .processPayment(totalAmount);

        if (paymentResponse.isSuccess()) {
            order.setStatus("CONFIRMED");
            order.setTransactionId(paymentResponse.getTransactionId());
        } else {
            order.setStatus("FAILED");
            order.setTransactionId(null); // Or keep it null
        }

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
