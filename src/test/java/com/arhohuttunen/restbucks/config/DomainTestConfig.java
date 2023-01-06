package com.arhohuttunen.restbucks.config;

import com.arhohuttunen.restbucks.application.out.InMemoryOrders;
import com.arhohuttunen.restbucks.application.out.InMemoryPayments;
import com.arhohuttunen.restbucks.application.out.Orders;
import com.arhohuttunen.restbucks.application.out.Payments;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.arhohuttunen.restbucks.application")
public class DomainTestConfig {
    @Bean
    Orders orders() {
        return new InMemoryOrders();
    }

    @Bean
    Payments payments() {
        return new InMemoryPayments();
    }
}
