package com.arhohuttunen.restbucks.adapter.in.rest.resource;

import com.arhohuttunen.restbucks.application.order.Order;
import com.arhohuttunen.restbucks.shared.Location;

import java.util.List;

public record OrderRequest(Location location, List<OrderItemRequest> items) {
    public Order toDomain() {
        return new Order(location, items.stream().map(OrderItemRequest::toDomain).toList());
    }
}
