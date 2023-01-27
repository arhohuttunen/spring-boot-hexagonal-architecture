package com.arhohuttunen.restbucks.adapter.out.persistence.config;

import com.arhohuttunen.restbucks.shared.DomainService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class TransactionalDomainServiceAspect {

    private final TransactionalDomainServiceExecutor transactionalDomainServiceExecutor;

    @Pointcut("@within(domainService)")
    void inDomainService(DomainService domainService) {

    }

    @Around("inDomainService(domainService)")
    Object domainService(ProceedingJoinPoint proceedingJoinPoint, DomainService domainService) {
        return transactionalDomainServiceExecutor.executeInTransaction(() -> {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
}
