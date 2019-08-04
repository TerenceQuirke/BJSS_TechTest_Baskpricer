package com.bjss.techtest.basketpricer.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class CurrencyHandler {
    public CurrencyHandler() {
    }

    public String currencyFormatter(BigDecimal amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
        return format.format(amount);
    }

    public BigDecimal sumBigDecimals(BigDecimal[] bigDecimalsArray){

        BigDecimal filteredSumOfBigDecimals = Arrays.stream(bigDecimalsArray)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return filteredSumOfBigDecimals;
    }

}