package com.arhohuttunen.coffeeshop.adapter.out.persistence;

import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.application.order.LineItem;
import com.arhohuttunen.coffeeshop.application.out.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrdersJpaAdapter implements Orders {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order findOrderById(UUID orderId) {
        return orderJpaRepository.findById(orderId)
                .map(this::toDomain)
                .orElseThrow();
    }

    @Override
    public Order save(Order order) {
        return toDomain(orderJpaRepository.save(toEntity(order)));
    }

    @Override
    public void deleteById(UUID orderId) {
        orderJpaRepository.deleteById(orderId);
    }

    private OrderEntity toEntity(Order order) {
        var entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setLocation(order.getLocation());
        entity.setStatus(order.getStatus());
        entity.setItems(order.getItems().stream().map(this::toEntity).toList());
        return entity;
    }

    private LineItemEntity toEntity(LineItem lineItem) {
        var entity = new LineItemEntity();
        entity.setDrink(lineItem.drink());
        entity.setQuantity(lineItem.quantity());
        entity.setMilk(lineItem.milk());
        entity.setSize(lineItem.size());
        return entity;
    }

    private Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getLocation(),
                entity.getItems().stream().map(this::toDomain).toList(),
                entity.getStatus()
        );
    }

    private LineItem toDomain(LineItemEntity entity) {
        return new LineItem(
                entity.getDrink(),
                entity.getQuantity(),
                entity.getMilk(),
                entity.getSize()
        );
    }
}
