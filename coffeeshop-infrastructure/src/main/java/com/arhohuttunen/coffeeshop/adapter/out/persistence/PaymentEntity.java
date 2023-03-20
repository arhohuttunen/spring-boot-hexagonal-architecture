package com.arhohuttunen.coffeeshop.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PaymentEntity {
    @Id
    private UUID id;

    @NotNull
    private UUID orderId;

    @NotNull
    private String cardHolderName;

    @NotNull
    private String cardNumber;

    @NotNull
    private Month expiryMonth;

    @NotNull
    private Year expiryYear;

    @NotNull
    private LocalDate paid;
}
