package com.arhohuttunen.restbucks.adapter.out.persistence;

import com.arhohuttunen.restbucks.application.order.Order;
import com.arhohuttunen.restbucks.application.order.OrderItem;
import com.arhohuttunen.restbucks.application.out.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrdersJpaAdapter implements Orders {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order findOrderById(UUID orderId) {
        return orderJpaRepository.findById(orderId).map(this::toDomain).orElseThrow();
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

    private OrderItemEntity toEntity(OrderItem orderItem) {
        var entity = new OrderItemEntity();
        entity.setDrink(orderItem.drink());
        entity.setQuantity(orderItem.quantity());
        entity.setMilk(orderItem.milk());
        entity.setSize(orderItem.size());
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

    private OrderItem toDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getDrink(),
                entity.getQuantity(),
                entity.getMilk(),
                entity.getSize()
        );
    }
}
