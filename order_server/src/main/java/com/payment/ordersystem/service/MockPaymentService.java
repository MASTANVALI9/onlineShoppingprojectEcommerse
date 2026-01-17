package com.payment.ordersystem.service;

import com.payment.ordersystem.dto.response.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Random;

@Service
public class MockPaymentService implements PaymentService {

    @Override
    public PaymentResponse processPayment(double amount) {
        // Simulate external API call latency
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Simulate random success/failure (90% success rate)
        // BUT: if amount > 5000, always FAIL (for testing)
        boolean isSuccess;
        if (amount > 5000) {
            isSuccess = false;
        } else {
            isSuccess = new Random().nextInt(10) != 0;
        }

        String transactionId = isSuccess ? UUID.randomUUID().toString() : null;

        return new PaymentResponse(isSuccess, transactionId);
    }
}
