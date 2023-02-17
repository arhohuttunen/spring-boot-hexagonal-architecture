package com.arhohuttunen.restbucks.application.payment;

import java.time.LocalDate;
import java.util.UUID;

public class Payment {
    private UUID id = UUID.randomUUID();
    private final UUID orderId;
    private final CreditCard creditCard;
    private final LocalDate paid;

    public Payment(UUID orderId, CreditCard creditCard, LocalDate paid) {
        this.orderId = orderId;
        this.creditCard = creditCard;
        this.paid = paid;
    }
    
    public UUID getId() {
        return this.id;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public CreditCard getCreditCard() {
        return this.creditCard;
    }

    public LocalDate getPaid() {
        return this.paid;
    }
}
