package com.arhohuttunen.restbucks.adapter.out.persistence;

import com.arhohuttunen.restbucks.shared.Drink;
import com.arhohuttunen.restbucks.shared.Milk;
import com.arhohuttunen.restbucks.shared.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class OrderItemEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated
    @NotNull
    private Drink drink;
    @NotNull
    private Integer quantity;
    @Enumerated
    @NotNull
    private Size size;
    @Enumerated
    @NotNull
    private Milk milk;
}
