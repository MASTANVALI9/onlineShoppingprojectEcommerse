package com.payment.ordersystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
