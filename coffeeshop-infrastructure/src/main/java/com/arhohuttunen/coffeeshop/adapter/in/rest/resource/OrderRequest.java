package com.arhohuttunen.coffeeshop.adapter.in.rest.resource;

import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.shared.Location;

import java.util.List;

public record OrderRequest(Location location, List<OrderItemRequest> items) {
    public Order toDomain() {
        return new Order(location, items.stream().map(OrderItemRequest::toDomain).toList());
    }
}
