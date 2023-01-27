package com.arhohuttunen.restbucks.config;

import com.arhohuttunen.restbucks.shared.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.arhohuttunen.restbucks.application",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = DomainService.class)
)
public class DomainConfig {
}
