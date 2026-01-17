package com.payment.ordersystem.service;

import com.payment.ordersystem.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(double amount);
}
