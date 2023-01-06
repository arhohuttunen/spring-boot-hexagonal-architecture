package com.arhohuttunen.restbucks.application.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Payment {
    private UUID id = UUID.randomUUID();
    private final UUID orderId;
    private final CreditCard creditCard;
    private final LocalDate paid;
}
