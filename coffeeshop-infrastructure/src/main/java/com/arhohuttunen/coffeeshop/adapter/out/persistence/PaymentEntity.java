package com.arhohuttunen.coffeeshop.adapter.out.persistence;

import com.arhohuttunen.coffeeshop.application.payment.CreditCard;
import com.arhohuttunen.coffeeshop.application.payment.Payment;
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

    public Payment toDomain() {
        return new Payment(
                id,
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
        entity.setId(payment.getId());
        entity.setOrderId(payment.getOrderId());
        entity.setCardHolderName(payment.getCreditCard().cardHolderName());
        entity.setCardNumber(payment.getCreditCard().cardNumber());
        entity.setExpiryMonth(payment.getCreditCard().expiryMonth());
        entity.setExpiryYear(payment.getCreditCard().expiryYear());
        entity.setPaid(payment.getPaid());
        return entity;
    }
}
