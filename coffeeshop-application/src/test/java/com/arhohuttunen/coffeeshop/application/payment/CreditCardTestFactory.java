package com.arhohuttunen.coffeeshop.application.payment;

import java.time.Month;
import java.time.Year;

public class CreditCardTestFactory {
    public static CreditCard aCreditCard() {
        return new CreditCard("Michael Faraday", "11223344", Month.JANUARY, Year.of(2023));
    }
}
