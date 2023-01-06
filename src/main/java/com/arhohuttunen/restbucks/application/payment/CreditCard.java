package com.arhohuttunen.restbucks.application.payment;

import java.time.Month;
import java.time.Year;

public record CreditCard(String cardHolderName, String cardNumber, Month expiryMonth, Year expiryYear) {}
