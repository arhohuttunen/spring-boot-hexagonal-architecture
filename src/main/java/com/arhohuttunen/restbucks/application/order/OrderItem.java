package com.arhohuttunen.restbucks.application.order;

import com.arhohuttunen.restbucks.shared.Drink;
import com.arhohuttunen.restbucks.shared.Milk;
import com.arhohuttunen.restbucks.shared.Size;

import java.math.BigDecimal;

public record OrderItem(Drink drink, int quantity, Milk milk, Size size) {
    // For simplicity every small drink costs 4.0 and large 5.0
    BigDecimal getCost() {
        var price = BigDecimal.valueOf(4.0);
        if (size == Size.LARGE) {
            price = price.add(BigDecimal.ONE);
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
