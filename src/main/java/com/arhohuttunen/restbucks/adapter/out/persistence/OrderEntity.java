package com.arhohuttunen.restbucks.adapter.out.persistence;

import com.arhohuttunen.restbucks.shared.Location;
import com.arhohuttunen.restbucks.shared.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class OrderEntity {
    @Id
    private UUID id;
    @Enumerated
    @NotNull
    private Location location;
    @Enumerated
    @NotNull
    private Status status;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> items;
}
