package com.arhohuttunen.restbucks.application.out;

import com.arhohuttunen.restbucks.application.payment.Payment;

import java.util.UUID;

public interface Payments {
    Payment findPaymentByOrderId(UUID orderId);
    Payment save(Payment payment);
}
