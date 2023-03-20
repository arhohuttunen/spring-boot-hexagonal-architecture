package com.arhohuttunen.coffeeshop.adapter.out.persistence;

import com.arhohuttunen.coffeeshop.application.out.Payments;
import com.arhohuttunen.coffeeshop.application.payment.CreditCard;
import com.arhohuttunen.coffeeshop.application.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentsJpaAdapter implements Payments {
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment findPaymentByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId).map(this::toDomain).orElseThrow();
    }

    @Override
    public Payment save(Payment payment) {
        return toDomain(paymentJpaRepository.save(toEntity(payment)));
    }

    private PaymentEntity toEntity(Payment payment) {
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

    private Payment toDomain(PaymentEntity entity) {
        return new Payment(entity.getOrderId(), new CreditCard(entity.getCardHolderName(), entity.getCardNumber(), entity.getExpiryMonth(), entity.getExpiryYear()), entity.getPaid());
    }
}
