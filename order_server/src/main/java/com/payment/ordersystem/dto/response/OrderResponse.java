package com.payment.ordersystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String customerId;
    private Double totalAmount;
    private String status;
    private String transactionId;
    private List<ItemResponse> items;
}
