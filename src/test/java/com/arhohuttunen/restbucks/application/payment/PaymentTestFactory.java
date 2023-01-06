package com.arhohuttunen.restbucks.application.payment;

import com.arhohuttunen.restbucks.application.order.Order;

import java.time.LocalDate;

import static com.arhohuttunen.restbucks.application.payment.CreditCardTestFactory.aCreditCard;

public class PaymentTestFactory {
    public static Payment aPaymentForOrder(Order order) {
        return new Payment(order.getId(), aCreditCard(), LocalDate.now());
    }
}
