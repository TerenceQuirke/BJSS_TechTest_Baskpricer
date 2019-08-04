package com.bjss.techtest.basketpricer.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyHandler {
    public CurrencyHandler() {
    }

    public String currencyFormatter(BigDecimal amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
        return format.format(amount);
    }
}