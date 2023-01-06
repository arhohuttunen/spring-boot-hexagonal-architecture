package com.arhohuttunen.restbucks.adapter.in.rest.resource;

import com.arhohuttunen.restbucks.application.payment.Receipt;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceiptResponse(BigDecimal amount, LocalDate paid) {
    public static ReceiptResponse fromDomain(Receipt receipt) {
        return new ReceiptResponse(receipt.amount(), receipt.paid());
    }
}
