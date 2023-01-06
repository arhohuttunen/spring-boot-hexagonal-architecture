package com.arhohuttunen.restbucks.adapter.in.rest.resource;

import com.arhohuttunen.restbucks.application.order.Order;
import com.arhohuttunen.restbucks.shared.Location;
import com.arhohuttunen.restbucks.shared.Status;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Location location, List<OrderItemResponse> items, BigDecimal cost, Status status) {
    public static OrderResponse fromDomain(Order order) {
        return new OrderResponse(order.getLocation(), order.getItems().stream().map(OrderItemResponse::fromDomain).toList(), order.getCost(), order.getStatus());
    }
}
