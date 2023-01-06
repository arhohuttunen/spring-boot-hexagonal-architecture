package com.arhohuttunen.restbucks.adapter.in.rest.resource;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull String cardHolderName,
        @NotNull String cardNumber,
        @NotNull Integer expiryMonth,
        @NotNull Integer expiryYear) {}
