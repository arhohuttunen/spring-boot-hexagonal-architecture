package com.arhohuttunen.coffeeshop.adapter.in.rest.resource;

import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.shared.Location;
import com.arhohuttunen.coffeeshop.shared.Status;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Location location, List<OrderItemResponse> items, BigDecimal cost, Status status) {
    public static OrderResponse fromDomain(Order order) {
        return new OrderResponse(
                order.getLocation(),
                order.getItems().stream().map(OrderItemResponse::fromDomain).toList(),
                order.getCost(),
                order.getStatus()
        );
    }
}
