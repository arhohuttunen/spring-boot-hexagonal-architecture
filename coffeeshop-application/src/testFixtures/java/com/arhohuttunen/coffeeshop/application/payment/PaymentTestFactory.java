package com.arhohuttunen.coffeeshop.application.payment;

import com.arhohuttunen.coffeeshop.application.order.Order;

import java.time.LocalDate;

import static com.arhohuttunen.coffeeshop.application.payment.CreditCardTestFactory.aCreditCard;

public class PaymentTestFactory {
    public static Payment aPaymentForOrder(Order order) {
        return new Payment(order.getId(), aCreditCard(), LocalDate.now());
    }
}
