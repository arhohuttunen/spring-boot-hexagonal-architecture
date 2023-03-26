package com.arhohuttunen.coffeeshop.adapter.out.persistence.entity;

import com.arhohuttunen.coffeeshop.application.payment.CreditCard;
import com.arhohuttunen.coffeeshop.application.payment.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
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

    public Payment toDomain() {
        return new Payment(
                orderId,
                new CreditCard(
                        cardHolderName,
                        cardNumber,
                        expiryMonth,
                        expiryYear
                ),
                paid
        );
    }

    public static PaymentEntity fromDomain(Payment payment) {
        var entity = new PaymentEntity();
        entity.setOrderId(payment.orderId());
        entity.setCardHolderName(payment.creditCard().cardHolderName());
        entity.setCardNumber(payment.creditCard().cardNumber());
        entity.setExpiryMonth(payment.creditCard().expiryMonth());
        entity.setExpiryYear(payment.creditCard().expiryYear());
        entity.setPaid(payment.paid());
        return entity;
    }
}
