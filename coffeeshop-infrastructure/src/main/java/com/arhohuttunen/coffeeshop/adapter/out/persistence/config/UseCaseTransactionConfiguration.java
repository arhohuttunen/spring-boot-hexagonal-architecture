package com.arhohuttunen.coffeeshop.adapter.out.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class UseCaseTransactionConfiguration {
    @Bean
    TransactionalUseCaseAspect transactionalUseCaseAspect(TransactionalUseCaseExecutor transactionalUseCaseExecutor) {
        return new TransactionalUseCaseAspect(transactionalUseCaseExecutor);
    }

    @Bean
    TransactionalUseCaseExecutor transactionalUseCaseExecutor() {
        return new TransactionalUseCaseExecutor();
    }
}
